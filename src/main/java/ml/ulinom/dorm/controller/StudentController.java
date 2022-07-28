package ml.ulinom.dorm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import ml.ulinom.dorm.entity.Dorm;
import ml.ulinom.dorm.entity.Student;
import ml.ulinom.dorm.entity.vo.StudentQueryVO;
import ml.ulinom.dorm.service.DormService;
import ml.ulinom.dorm.service.StudentService;
import ml.ulinom.dorm.utils.ExcelUtils;
import ml.ulinom.dorm.utils.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ulinom
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/dorm/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormService dormService;

    @RequestMapping("/list")
    public ResultVO list(@RequestParam("page") String page, @RequestParam("limit") String limit) {
        List<Student> students = studentService.list(null);
        System.out.println(students);
        return ResultVO.ok().data("item", students);
    }

    @ApiOperation(value = "分页查询学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "limit", value = "每页记录")
    })
    @GetMapping("/pageStudent")
    public ResultVO pageListStudent(@RequestParam long page, @RequestParam long limit) {
        //创建page对象
        Page<Student> studentPage = new Page<>(page, limit);

        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到teacherPage对象里面
        studentService.page(studentPage, null);
        //总记录数
        long total = studentPage.getTotal();
        //数据list集合
        List<Student> records = studentPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

    // 带条件分页查询讲师
    @ApiOperation(value = "带条件分页查询学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页"),
            @ApiImplicitParam(name="limit",value = "每页记录")
    })
    @PostMapping("/pageStudentCondition")
    public ResultVO pageStudentCondition(@RequestBody(required = false) StudentQueryVO studentQueryVO){
        //创建page对象
        Page<Student> studentPage =new Page<>(studentQueryVO.getPage(),studentQueryVO.getLimit());
        //构建条件
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = studentQueryVO.getStudentName();
        String id = studentQueryVO.getId();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("student_name",name);
        }
        if(!StringUtils.isEmpty(id)){
            wrapper.eq("id", id);
        }
        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到studentPage对象里面
        studentService.page(studentPage,wrapper);
        //总记录数
        long total = studentPage.getTotal();
        //数据list集合
        List<Student> records = studentPage.getRecords();
        Map map=new HashMap();
        map.put("count",total);
        map.put("item",records);
        return ResultVO.ok().data(map);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable String id) {
        if (studentService.removeById(id)) {
            return ResultVO.ok().message("学生删除成功");
        } else return ResultVO.error();
    }

    @DeleteMapping("/removeList")
    public ResultVO removeList(@RequestBody String ids) {
        String[] idList = ids.split(",");
        if (studentService.removeByIds(Arrays.asList(idList))) {
            return ResultVO.ok();
        } else return ResultVO.error();
    }

    @PostMapping("/addStudent")
    @ResponseBody
    public ResultVO addStudent(String sName, String dormNumber) {
        if (dormNumber.equals("暂不分配")) {
            Student student = new Student(sName, "-1");
            if (studentService.save(student)) {
                return ResultVO.ok().message("学生添加成功");
            } else return ResultVO.error();
        } else {
            QueryWrapper<Dorm> wrapper = new QueryWrapper<>();
            wrapper.eq("dorm_number", dormNumber);
            List<Dorm> list = dormService.list(wrapper);
            if (list.size() <= 0) return ResultVO.error();
            Student student = new Student(sName, list.get(0).getId());
            if (studentService.save(student)) {
                return ResultVO.ok().message("学生添加成功");
            } else return ResultVO.error();
        }
    }

    @PostMapping("/updateStudent")
    public ResultVO updateStudent(String id, String sName, String dormId){
        Student student = new Student(id, sName, dormId);
        if (studentService.updateById(student)) {
            return ResultVO.ok().message("修改成功");
        } else return ResultVO.error();
    }

    @PostMapping("/addExcel")
    @ResponseBody
    public ResultVO addExcel(@RequestParam(value = "file") MultipartFile file) {
        ResultVO resultVO = ResultVO.ok();
        try {
            List<List<Object>> lists = ExcelUtils.getUserListByExcel(file.getInputStream(), file.getOriginalFilename());
            //List<List<Object>>--->List<Student>
            List<Student> students = new ArrayList<>();
            //List<Object>
            for (List<Object> ob : lists) {
                Student student = new Student(ob.get(0).toString(), ob.get(1).toString());
                students.add(student);
            }
            System.out.println(students);
            if (studentService.saveBatch(students)) {
                return ResultVO.ok().message("添加成功");
            } else return ResultVO.error();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;
    }
    @GetMapping("/findRoommates")
    public ResultVO findRoommates(String id) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.inSql("dorm_id", "SELECT dorm_id FROM student WHERE dorm_id != "+"-1"+" and id = " + id);
        List<Student> list = studentService.list(wrapper);
        Map map = new HashMap();
        map.put("item", list);
        return ResultVO.ok().data(map);
    }
}
