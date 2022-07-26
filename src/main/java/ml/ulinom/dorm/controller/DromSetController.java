
package ml.ulinom.dorm.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ml.ulinom.dorm.entity.Dorm;
import ml.ulinom.dorm.entity.vo.DormQueryVO;
import ml.ulinom.dorm.service.DormService;
import ml.ulinom.dorm.service.StudentService;
import ml.ulinom.dorm.utils.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping({"/dorm/dormset"})
public class DromSetController {
    @Autowired
    private DormService dormService;
    @Autowired
    private StudentService stuSer;

    public DromSetController() {
    }

    @RequestMapping({"/ini"})
    public ModelAndView ini() {
        return new ModelAndView("dormedit");
    }

    @RequestMapping({"/list"})
    public ResultVO list(@RequestParam("page") String page, @RequestParam("limit") String limit) {
        List<Dorm> dorms = this.dormService.list((Wrapper)null);
        System.out.println(dorms);
        return ResultVO.ok().data("item", dorms);
    }

    @ApiOperation("分页查询宿舍")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "page",
            value = "当前页"
    ), @ApiImplicitParam(
            name = "limit",
            value = "每页记录"
    )})
    @GetMapping({"/pagedorm"})
    public ResultVO pageListDorm(@RequestParam long page, @RequestParam long limit) {
        Page<Dorm> dormPage = new Page(page, limit);
        this.dormService.page(dormPage, (Wrapper)null);
        long total = dormPage.getTotal();
        List<Dorm> records = dormPage.getRecords();
        HashMap<String, Object> map = new HashMap();
        map.put("count", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

    @ApiOperation("带条件分页查询dorm")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "page",
            value = "当前页"
    ), @ApiImplicitParam(
            name = "limit",
            value = "每页记录"
    )})
    @PostMapping({"/pageDormCondition"})
    public ResultVO pageStudentCondition(@RequestBody(required = false) DormQueryVO dormQueryVO) {
        Page<Dorm> dormPage = new Page(dormQueryVO.getPage(), dormQueryVO.getLimit());
        QueryWrapper<Dorm> wrapper = new QueryWrapper();
        String name = dormQueryVO.getDormNumber();
        String id = dormQueryVO.getId();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("dorm_number", name);
        }

        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }

        this.dormService.page(dormPage, wrapper);
        long total = dormPage.getTotal();
        List<Dorm> records = dormPage.getRecords();
        Map map = new HashMap();
        map.put("count", total);
        map.put("item", records);
        return ResultVO.ok().data(map);
    }

    @ApiOperation("修改学生宿舍属性值")
    @PostMapping({"/edit"})
    public ResultVO Dormset(String id, String water, String ele) {
        UpdateWrapper<Dorm> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        System.out.println("dormid = " + id);
        Dorm dorm = new Dorm();
        dorm.setElectricBill(BigDecimal.valueOf(Long.parseLong(water)));
        dorm.setWaterBill(BigDecimal.valueOf(Long.parseLong(ele)));
        boolean rows = this.dormService.update(dorm, updateWrapper);
        System.out.println("rows = " + rows);
        return rows ? ResultVO.ok().message("修改成功") : ResultVO.error();
    }
}
