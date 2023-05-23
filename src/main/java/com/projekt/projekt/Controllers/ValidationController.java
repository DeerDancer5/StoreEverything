package com.projekt.projekt.Controllers;

import com.projekt.projekt.Validation.Person;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Controller
public class ValidationController implements WebMvcConfigurer {



    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping("/register")
    public String checkPersonInfo(@Validated @ModelAttribute("person") Person person, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "register";
        }

        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("person", new Person());
        return "login";
    }

    @PostMapping("/login")
    public String checkPerson(@Validated @ModelAttribute("person") Person person, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "login";
        }

        return "redirect:/notes";
    }
}