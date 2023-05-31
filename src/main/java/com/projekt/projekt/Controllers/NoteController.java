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

import java.time.LocalDateTime;
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

        Page <Note> notes = noteService.getNotesPage(page.orElse(0),pageSize.orElse(10),
                sortBy.orElse("date"),sortDir.orElse("desc"), selectedCategory.orElse(""));
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
        model.addAttribute("Name","Your notes");
        model.addAttribute("NotesList",notes);
        model.addAttribute("page", page.orElse(0));
        model.addAttribute("pageSize",pageSize.orElse(10));
        model.addAttribute("sortBy",sortBy.orElse("date"));
        model.addAttribute("sortDir",sortDir.orElse("desc"));
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
        mav.addObject("Name","Your notes");
        mav.addObject("NotesList",notes);

        return mav;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView editNote(@PathVariable Long id,Optional<String> newCategory) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("edit");
        boolean isNewCategory=false;
        if(newCategory.isPresent()) {
            isNewCategory=true;
        }
        Optional<Note> note = noteService.getById(id);

        List <Category> categoryList = categoryService.getAllCategories();
        mav.addObject("title",note.get().getTitle());
        mav.addObject("noteCategory",note.get().getCategory().getName());
        mav.addObject("date",note.get().dateToString());
        mav.addObject("content",note.get().getContent());
        mav.addObject("categoryList",categoryList);
        mav.addObject("note",note);
        mav.addObject("isNewCategory",isNewCategory);

        return mav;
    }
    @PostMapping("/edit")
    public String saveEdited(Note edited){
        Note note = noteService.getById(edited.getId()).orElseThrow();
        note.setTitle(edited.getTitle());
        note.setContent(edited.getContent());

        if(categoryService.getByName(edited.getCategoryName()).isPresent()){
            note.setCategory(categoryService.getByName(edited.getCategoryName()).orElseThrow());
        }
        else {
            Category category = new Category(edited.getCategoryName());
            categoryService.save(category);
            note.setCategory(category);
        }


        noteService.save(note);
        return "redirect:/notes";
    }
    @GetMapping("/add")
    public ModelAndView addNote(@RequestParam Optional <String> newCategory){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add");
        boolean isNewCategory=false;
        if(newCategory.isPresent()) {
            isNewCategory=true;
        }
        List <Category> categoryList = categoryService.getAllCategories();
        Note note = new Note();
        mav.addObject("note",note);
        mav.addObject("categoryList",categoryList);
        mav.addObject("isNewCategory",isNewCategory);
        mav.addObject("redirect","?newCategory=true");
        return mav;
    }
    @PostMapping("/add")
    public String save(Note note){
        if(categoryService.getByName(note.getCategoryName()).isPresent()){
            note.setCategory(categoryService.getByName(note.getCategoryName()).orElseThrow());
        }
        else {
            Category category = new Category(note.getCategoryName());
            categoryService.save(category);
            note.setCategory(category);
        }
        note.setDate(LocalDateTime.now());
        noteService.save(note);
        return "redirect:/notes";
    }
    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id){
        Note note = noteService.getById(id).orElseThrow();
        noteService.delete(note);
        return "redirect:/notes";
    }

}
