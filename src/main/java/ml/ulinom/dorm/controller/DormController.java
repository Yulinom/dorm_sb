package ml.ulinom.dorm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ml.ulinom.dorm.entity.Dorm;
import ml.ulinom.dorm.entity.Student;
import ml.ulinom.dorm.entity.vo.TransferVO;
import ml.ulinom.dorm.service.DormService;
import ml.ulinom.dorm.service.StudentService;
import ml.ulinom.dorm.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ulinom
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/dorm/dorm")
public class DormController {
    @Autowired
    private DormService dormService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResultVO dormMates(@PathVariable String id) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("dorm_id", id);
        List<Student> students = studentService.list(studentQueryWrapper);
        return ResultVO.ok().data("item", students);
    }

    @DeleteMapping("/removeList")
    public ResultVO removeStudentList(@RequestBody String ids) {
        String[] idList = ids.split(",");
        Student student = new Student();

        for (String s : idList) {
            student.setId(s);
            student.setDormId("-1");
            if (!studentService.updateById(student)) {
                //TODO 这里应该配置事务
                break;
            }
        }
        return ResultVO.ok();
    }

    @DeleteMapping("/{id}")
    public ResultVO removeStudent(@PathVariable String id) {
        Student student = new Student();
        student.setId(id);
        student.setDormId("-1");
        if (studentService.updateById(student)) {
            return ResultVO.ok();
        } else return ResultVO.error();
    }

    @PutMapping("/autoAddStudents")
    public ResultVO autoAddStudents(String ids, Integer count) {
        String[] idList = ids.split(",");
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>().eq("dorm_id", "-1");
        List<Student> studentList = studentService.list(wrapper);

        int needCount = idList.length * count;
        if (needCount > studentList.size())
            return ResultVO.error().message("人数不足，可分配学生人数为：" + studentList.size());
        else {
            for (String s : idList) {
                for (Student student : studentList) {
                    student.setDormId(s);
                    studentService.updateById(student);
                }
            }
            return ResultVO.ok();
        }
    }

    @GetMapping("/isAddStudent")
    public ResultVO isAddStudent(){
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>().eq("dorm_id", -1);
        List<Student> students = studentService.list(wrapper);
        return ResultVO.ok().data("item",students);
    }

    @PutMapping("/addStudents")
    public ResultVO addStudents(@RequestBody TransferVO vo){
        List<Map> data = vo.getGetData();
        Student student = new Student();
        for (Map map : data) {
            student.setId((String) map.get("value")).setDormId(vo.getDormId());
            studentService.updateById(student);
        }
        return ResultVO.ok();
    }

    @GetMapping("/show")
    public ResultVO show(){
        QueryWrapper<Dorm> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "dorm_number title");
        List<Map<String, Object>> dorms = dormService.listMaps(queryWrapper);

        HashMap<String, Object> checked = new HashMap<>();
        checked.put("id","-1");
        checked.put("title","暂不分配");

        dorms.add(0,checked);
        return ResultVO.ok().data("item",dorms);
    }
}

