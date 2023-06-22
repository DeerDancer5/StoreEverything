package com.projekt.projekt.Controllers;

import com.projekt.projekt.Services.CategoryService;
import com.projekt.projekt.Services.NoteService;
import com.projekt.projekt.Services.UserService;
import com.projekt.projekt.Users.User;
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
    @Autowired
    private UserService userService;

    @GetMapping("/addUser")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/addUser")
    public String checkPersonInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "register";
        }
        user.setRoles("ROLE_USER");
        userService.save(user);

        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String checkPerson(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "redirect:/notes";
    }
    @GetMapping("/")
    public String homepage() {
        return "/homepage";
    }

    @GetMapping("/homepage")
    public String Homepage() {
        return "homepage";
    }

    @GetMapping("/navbar")
    public String Navbar()
    {
        return "navbar";
    }





}