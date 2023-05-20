package com.projekt.projekt.Controllers;
import com.projekt.projekt.Notes.Note;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
public class UserController {
    @GetMapping
    @RequestMapping("/")
    String getUser(Model model) {
        model.addAttribute("Name","UserName");

        model.addAttribute("Notes", Arrays.asList(
                new Note("note1","sport","content1", LocalDateTime.now()),
                new Note("note2","music","content2", LocalDateTime.now()),
                new Note("note3","music","content3", LocalDateTime.now()),
                new Note("note4","sport","content4", LocalDateTime.now())
        ));
        return "user";
    }
}
