package ml.ulinom.dorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String longin(String user, String password, HttpSession session) {
        session.setAttribute("token", UUID.randomUUID());
        if (user.equals("admin") && password.equals("admin")) {
            return "index";
        } else return "login";

    }

}
