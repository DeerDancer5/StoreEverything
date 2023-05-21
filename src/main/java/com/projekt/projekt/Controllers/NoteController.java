package com.projekt.projekt.Controllers;
import com.projekt.projekt.Notes.Note;
import com.projekt.projekt.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping
    private String getNotes(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy, Model model) {

        Page <Note> notes = service.getAllNotes(page, sortBy);
        model.addAttribute("Name","UserName");
        model.addAttribute("NotesList",notes);

        return "notes";
    }


}
