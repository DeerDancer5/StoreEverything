package com.projekt.projekt.Controllers;
import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Notes.Note;
import com.projekt.projekt.Notes.NoteRequest;
import com.projekt.projekt.Services.CategoryService;
import com.projekt.projekt.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    private String getNotes(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> sortDir,
            @RequestParam Optional<String []> category,
            Model model) {
        Page <Note> notes = noteService.getNotesPage(page.orElse(0),pageSize.orElse(6),
                sortBy.orElse("id"),sortDir.orElse("asc"),category.orElse(new String[0]));
        List <Category> categoryList = categoryService.getAllCategories();
        Collections.sort(categoryList);
        List<String> sortOptions = noteService.getSortOptions();
        List<Integer> sizeOptions = noteService.getSizeOptions();
        int numberOfPages= notes.getTotalPages()-1;
        int numberOfElements = (int) notes.getTotalElements();
        model.addAttribute("Category",category.orElse(new String[0]));
        model.addAttribute("CategoryList",categoryList);
        model.addAttribute("SortOptions",sortOptions);
        model.addAttribute("SizeOptions",sizeOptions);
        model.addAttribute("NumberOfPages",numberOfPages);
        model.addAttribute("NumberOfElements",numberOfElements);
        model.addAttribute("Name","UserName");
        model.addAttribute("NotesList",notes);
        model.addAttribute("page", page.orElse(0));
        model.addAttribute("pageSize",pageSize.orElse(6));
        model.addAttribute("sortBy",sortBy.orElse("id"));
        model.addAttribute("sortDir",sortDir.orElse("asc"));
        model.addAttribute("noteRequest",new NoteRequest());

        return "notes";
    }
    @PostMapping()
    public String reload(@ModelAttribute NoteRequest noteRequest){
        if(noteRequest.getSortBy().equals("--")){
            noteRequest.setSortBy("id");
        }
        String redirect = "redirect:/notes?sortBy="+noteRequest.getSortBy().toLowerCase()+"&pageSize="+noteRequest.getPageSize()
                +"&page=0&sortDir="+noteRequest.getSortDir();
        return redirect;
    }


}
