package ml.ulinom.dorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

//    @RequestMapping("/login")
    @PostMapping("/login")
    public String longin(String user, String password, HttpSession session, HttpServletRequest request) {
//        session.setAttribute("token", UUID.randomUUID());   写在这里的话账号密码是否符合都会登录成功
        if (user.equals("admin") && password.equals("admin")) {
            session.setAttribute("token", UUID.randomUUID());
//            return "/index";  采用post方法不能这样请求转发
            return "redirect:https://"+request.getServerName()+"/";
        }
//        else return "login";  采用post方法不能这样请求转发
        return "redirect:https://"+request.getServerName()+"/login";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

}
