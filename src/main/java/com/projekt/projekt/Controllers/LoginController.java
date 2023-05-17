package com.projekt.projekt.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String Login() {
        return "login.html";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome.html";
    }
}
