package com.projekt.projekt.Controllers;
import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Notes.Note;
import com.projekt.projekt.Notes.NoteRequest;
import com.projekt.projekt.Services.CategoryService;
import com.projekt.projekt.Services.NoteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping()
    public ModelAndView update(@ModelAttribute NoteRequest noteRequest, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notes");

        if(noteRequest.getPage()==null) {
            if(session.getAttribute("noteRequest")==null) {
                mav = addNewParameters(mav);

            }
            else{
                NoteRequest previous=(NoteRequest) session.getAttribute("noteRequest");
                mav=addRequestedParameters(mav,previous,session);
            }
        }
        else {
                addRequestedParameters(mav,noteRequest,session);

        }
        return mav;
    }
    public ModelAndView addNewParameters(ModelAndView mav){
        LocalDate startDate = noteService.dateFromString(noteService.getEarliestDate()).toLocalDate();
        LocalDate endDate = LocalDate.now();

        Page <Note> notes = noteService.getNotesPage(0,10,
                "date","desc", "",
                startDate,endDate);
        List <Category> categoryList = categoryService.getAllCategories();
        Collections.sort(categoryList);
        List<String> sortOptions = noteService.getSortOptions();
        List<Integer> sizeOptions = noteService.getSizeOptions();

        int numberOfPages= notes.getTotalPages()-1;
        int numberOfElements = (int) notes.getTotalElements();
        mav.addObject("selectedCategory", "");
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("CategoryList",categoryList);
        mav.addObject("SortOptions",sortOptions);
        mav.addObject("SizeOptions",sizeOptions);
        mav.addObject("NumberOfPages",numberOfPages);
        mav.addObject("NumberOfElements",numberOfElements);
        mav.addObject("Name","Your notes");
        mav.addObject("NotesList",notes);
        mav.addObject("page", 0);
        mav.addObject("pageSize",10);
        mav.addObject("sortBy","date");
        mav.addObject("sortDir","desc");
        mav.addObject("noteRequest",new NoteRequest());





        return mav;
    }
    public ModelAndView addRequestedParameters(ModelAndView mav, NoteRequest noteRequest, HttpSession session){

        String category = null;

        if (noteRequest.getSortBy().equals("--")) {
            noteRequest.setSortBy("id");
        }
        if (noteRequest.getSelectedCategory() != null) {

            category = String.join(",", noteRequest.getSelectedCategory());
        }

        System.out.println("start date:" + noteRequest.getStartDate());
        System.out.println("end date: " + noteRequest.getEndDate().toString());

        Page<Note> notes = noteService.getNotesPage(Integer.parseInt(noteRequest.getPage()), Integer.parseInt(noteRequest.getPageSize()),
                noteRequest.getSortBy(), noteRequest.getSortDir(), category, noteRequest.getStartDate(), noteRequest.getEndDate());
        List<Category> categoryList = categoryService.getAllCategories();
        Collections.sort(categoryList);
        List<String> sortOptions = noteService.getSortOptions();
        List<Integer> sizeOptions = noteService.getSizeOptions();
        NoteRequest request = new NoteRequest();
        request.setSelectedCategory(Arrays.copyOf(noteRequest.getSelectedCategory(), noteRequest.getSelectedCategory().length));
        int numberOfPages = notes.getTotalPages() - 1;
        int numberOfElements = (int) notes.getTotalElements();

        mav.addObject("page", Integer.parseInt(noteRequest.getPage()));
        mav.addObject("pageSize", Integer.parseInt(noteRequest.getPageSize()));
        mav.addObject("sortBy", noteRequest.getSortBy().toLowerCase());
        mav.addObject("sortDir", noteRequest.getSortDir());
        mav.addObject("selectedCategory", category);
        mav.addObject("CategoryList", categoryList);
        mav.addObject("SortOptions", sortOptions);
        mav.addObject("SizeOptions", sizeOptions);
        mav.addObject("NumberOfPages", numberOfPages);
        mav.addObject("NumberOfElements", numberOfElements);
        mav.addObject("Name", "Your notes");
        mav.addObject("NotesList", notes);
        mav.addObject("startDate", noteRequest.getStartDate());
        mav.addObject("endDate", noteRequest.getEndDate());
        mav.addObject("noteRequest", request);

        session.setAttribute("noteRequest",noteRequest);




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
    @GetMapping("/edit/newCategory/{id}")
    public ModelAndView editNoteWithNewCategory(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("editWithNewCategory");
        Optional<Note> note = noteService.getById(id);
        List <Category> categoryList = categoryService.getAllCategories();
        mav.addObject("title",note.get().getTitle());
        mav.addObject("noteCategory",note.get().getCategory().getName());
        mav.addObject("date",note.get().dateToString());
        mav.addObject("content",note.get().getContent());
        mav.addObject("categoryList",categoryList);
        mav.addObject("note",note);
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
    public ModelAndView addNote(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add");

        List <Category> categoryList = categoryService.getAllCategories();

        Note note = new Note();
        mav.addObject("note",note);
        mav.addObject("categoryList",categoryList);
        mav.addObject("redirect","?newCategory=true");
        return mav;
    }
    @GetMapping("/add/newCategory")
    public ModelAndView addNoteWithNewCategory(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addWithNewCategory");
        List <Category> categoryList = categoryService.getAllCategories();
        Note note = new Note();
        mav.addObject("note",note);
        mav.addObject("categoryList",categoryList);
        mav.addObject("redirect","?newCategory=true");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView saveNewNote(@Valid Note note, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("redirect:/notes");

        if (bindingResult.hasErrors()) {

                 ModelAndView tmp = new ModelAndView();
                 tmp.addObject("categoryList", categoryService.getAllCategories());
                 if(categoryService.getByName((String) bindingResult.getRawFieldValue("categoryName")).isPresent()){
                     tmp.setViewName("add");
                 }
                 else {
                     tmp.setViewName("addWithNewCategory");
                 }

                 return tmp;
        }

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
        return mav;
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id){
        Note note = noteService.getById(id).orElseThrow();
        noteService.delete(note);
        return "redirect:/notes";
    }

}
