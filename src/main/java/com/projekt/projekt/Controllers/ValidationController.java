package com.projekt.projekt.Controllers;

import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Notes.Note;
import com.projekt.projekt.Services.CategoryService;
import com.projekt.projekt.Services.NoteService;
import com.projekt.projekt.Validation.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
public class ValidationController implements WebMvcConfigurer {

    @Autowired
    private NoteService noteService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping("/register")
    public String checkPersonInfo(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            System.out.printf(String.valueOf(bindingResult));
            return "register";
        }
        System.out.println(person);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("person", new Person());
        return "login";
    }

    @PostMapping("/login")
    public String checkPerson(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            System.out.printf(String.valueOf(bindingResult));
            return "login";
        }
        System.out.println(person);
        return "redirect:/notes";
    }




}