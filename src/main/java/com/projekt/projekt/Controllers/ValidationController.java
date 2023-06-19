package com.projekt.projekt.Controllers;

import com.projekt.projekt.Services.CategoryService;
import com.projekt.projekt.Services.NoteService;
import com.projekt.projekt.Validation.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Controller
public class ValidationController implements WebMvcConfigurer {

    @Autowired
    private NoteService noteService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("person", new User());
        return "register";
    }

    @PostMapping("/register")
    public String checkPersonInfo(@Valid @ModelAttribute("person") User user, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            System.out.printf(String.valueOf(bindingResult));
            return "register";
        }
        System.out.println(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("person", new User());
        return "login";
    }

    @PostMapping("/login")
    public String checkPerson(@Valid @ModelAttribute("person") User user, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            System.out.printf(String.valueOf(bindingResult));
            return "login";
        }
        System.out.println(user);
        return "redirect:/notes";
    }




}