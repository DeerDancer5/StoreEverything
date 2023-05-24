package com.projekt.projekt.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/homepage")
    public String Homepage() {
        return "homepage.html";
    }

    @GetMapping("/navbar")
    public String Navbar()
    {
        return "navbar.html";
    }


}
