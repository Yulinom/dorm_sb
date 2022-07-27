package ml.ulinom.dorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class RouterController {
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String index(HttpSession session) {
        session.setAttribute("token", UUID.randomUUID());
        return "index";
    }

    @RequestMapping("/student-list")
    public String studentList() {
        return "student-list";
    }
    /*登陆*/
    @RequestMapping("/ini")
    public String index() {
        return "login";
    }
    /*宿舍修改水电路径*/
    @RequestMapping("/dorm-edit")
    public String dormEdit() {
        return "dormedit";
    }

    @RequestMapping("/roommates")
    public String roommates() {
        return "roommates";
    }

    @RequestMapping("/dormmates/{id}")
    public String dormmates(@PathVariable String id, Model model) {
        model.addAttribute("id",id);
        return "dormmates";
    }
}
