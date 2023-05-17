package com.projekt.projekt;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class UserController {
    @GetMapping
    @RequestMapping("/")
    String getUser(Model model) {
        model.addAttribute("Name","UserName");
        ArrayList<String> notes = new ArrayList<>();
        notes.add("Note1");
        notes.add("Note2");
        notes.add("Note3");
        notes.add("Note4");
        model.addAttribute("Notes",notes);
        return "user";
    }
}
