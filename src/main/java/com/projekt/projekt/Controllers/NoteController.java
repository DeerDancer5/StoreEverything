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
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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
            @RequestParam Optional <String> selectedCategory,
            Model model) {

        Page <Note> notes = noteService.getNotesPage(page.orElse(0),pageSize.orElse(6),
                sortBy.orElse("id"),sortDir.orElse("asc"), selectedCategory.orElse(""));
        List <Category> categoryList = categoryService.getAllCategories();
        Collections.sort(categoryList);
        List<String> sortOptions = noteService.getSortOptions();
        List<Integer> sizeOptions = noteService.getSizeOptions();
        int numberOfPages= notes.getTotalPages()-1;
        int numberOfElements = (int) notes.getTotalElements();
        model.addAttribute("selectedCategory", selectedCategory.orElse(""));
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

  /*  @PostMapping()
    public String reload(@ModelAttribute NoteRequest noteRequest,Model model){
        if(noteRequest.getSortBy().equals("--")){
            noteRequest.setSortBy("id");
        }

        String redirect = "redirect:/notes?sortBy="+noteRequest.getSortBy().toLowerCase()+"&pageSize="+noteRequest.getPageSize()
                +"&page=0&sortDir="+noteRequest.getSortDir();
        if(noteRequest.getSelectedCategory().length>0) {
            String selectedCategories = String.join(",", noteRequest.getSelectedCategory());
            System.out.println(model.getAttribute("pageSize"));
            redirect = redirect +"&selectedCategory="+selectedCategories;
        }

        return redirect;
    }*/
    @PostMapping()
    public ModelAndView update(@ModelAttribute NoteRequest noteRequest) {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("notes");
        String category=null;
        if(noteRequest.getSortBy().equals("--")){
            noteRequest.setSortBy("id");
        }
        if(noteRequest.getSelectedCategory()!=null) {

           category = String.join(",",noteRequest.getSelectedCategory());
        }

        Page <Note> notes = noteService.getNotesPage(Integer.parseInt(noteRequest.getPage()),Integer.parseInt(noteRequest.getPageSize()),
                noteRequest.getSortBy(),noteRequest.getSortDir(), category);
        List <Category> categoryList = categoryService.getAllCategories();
        Collections.sort(categoryList);
        List<String> sortOptions = noteService.getSortOptions();
        List<Integer> sizeOptions = noteService.getSizeOptions();
        NoteRequest request = new NoteRequest();
        request.setSelectedCategory(Arrays.copyOf(noteRequest.getSelectedCategory(),noteRequest.getSelectedCategory().length));
        int numberOfPages= notes.getTotalPages()-1;
        int numberOfElements = (int) notes.getTotalElements();
        mav.addObject("page",Integer.parseInt(noteRequest.getPage()));
        mav.addObject("pageSize",Integer.parseInt(noteRequest.getPageSize()));
        mav.addObject("sortBy",noteRequest.getSortBy().toLowerCase());
        mav.addObject("sortDir",noteRequest.getSortDir());
        mav.addObject("selectedCategory",category);
        mav.addObject("CategoryList",categoryList);
        mav.addObject("SortOptions",sortOptions);
        mav.addObject("SizeOptions",sizeOptions);
        mav.addObject("NumberOfPages",numberOfPages);
        mav.addObject("NumberOfElements",numberOfElements);
        mav.addObject("Name","UserName");
        mav.addObject("NotesList",notes);

        System.out.println(mav.getModel().get("sortDir"));
        return mav;
    }


}
