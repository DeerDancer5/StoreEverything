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
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy,
            Model model) {

        Page <Note> notes = service.getNotesPage(page.orElse(0),pageSize.orElse(5),sortBy.orElse("id"));
        int numberOfPages= (int) notes.getTotalPages()-1;
        model.addAttribute("NumberOfPages",numberOfPages);
        model.addAttribute("Name","UserName");
        model.addAttribute("NotesList",notes);
        model.addAttribute("page", page.orElse(0));
        model.addAttribute("pageSize",pageSize.orElse(5));
        model.addAttribute("sortBy",sortBy.orElse("id"));

        return "notes";
    }


}
