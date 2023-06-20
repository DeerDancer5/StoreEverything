package com.projekt.projekt.Controllers;
import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Notes.Note;
import com.projekt.projekt.Notes.NoteRequest;
import com.projekt.projekt.Repositories.UserRepository;
import com.projekt.projekt.Services.CategoryService;
import com.projekt.projekt.Services.NoteService;
import com.projekt.projekt.Validation.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
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
    @Autowired
    UserRepository userRepository;


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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Page <Note> notes = noteService.getNotesPage(0,10,
                "date","desc", "",
                startDate,endDate,userName);
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

        System.out.println(auth.getName());





        return mav;
    }
    public ModelAndView addRequestedParameters(ModelAndView mav, NoteRequest noteRequest, HttpSession session){

        String category = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if (noteRequest.getSortBy().equals("--")) {
            noteRequest.setSortBy("id");
        }
        if (noteRequest.getSelectedCategory() != null) {

            category = String.join(",", noteRequest.getSelectedCategory());
        }

        System.out.println("start date:" + noteRequest.getStartDate());
        System.out.println("end date: " + noteRequest.getEndDate().toString());

        Page<Note> notes = noteService.getNotesPage(Integer.parseInt(noteRequest.getPage()), Integer.parseInt(noteRequest.getPageSize()),
                noteRequest.getSortBy(), noteRequest.getSortDir(), category, noteRequest.getStartDate(), noteRequest.getEndDate(),userName);
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

        Optional<Note> note = noteService.getById(id);


        List <Category> categoryList = categoryService.getAllCategories();
        mav.addObject("title",note.get().getTitle());
        mav.addObject("noteCategory",note.get().getCategory().getName());
        mav.addObject("date",note.get().dateToString());
        mav.addObject("www",note.get().getWww());
        mav.addObject("content",note.get().getContent());
        mav.addObject("categoryList",categoryList);
        mav.addObject("note",note);

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
        mav.addObject("www",note.get().getWww());
        mav.addObject("content",note.get().getContent());
        mav.addObject("categoryList",categoryList);
        mav.addObject("note",note);
        return mav;
    }
    @PostMapping("/edit")
    public ModelAndView saveEdited(@Valid Note edited, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("redirect:/notes");
        if (bindingResult.hasErrors()) {
            System.out.println("blad");
            ModelAndView tmp = new ModelAndView();
            tmp.setViewName("edit");
            if(!categoryService.getByName(edited.getCategoryName()).isPresent()){
                tmp.setViewName("editWithNewCategory");
            }

            tmp.addObject("categoryList", categoryService.getAllCategories());
            Optional<Note> note = noteService.getById(edited.getId());

            tmp.addObject("title", bindingResult.getFieldValue("title"));
            tmp.addObject("noteCategory",edited.getCategoryName());
            tmp.addObject("date",bindingResult.getFieldValue("date"));
            tmp.addObject("www",bindingResult.getFieldValue("www"));
            tmp.addObject("content",bindingResult.getFieldValue("content"));
            tmp.addObject("note",note);
            //String redirect = "redirect:edit/"+edited.getId();


            return tmp;
        }
        Note note = noteService.getById(edited.getId()).orElseThrow();
        note.setTitle(edited.getTitle());
        note.setContent(edited.getContent());
        note.setWww(edited.getWww());


        if(categoryService.getByName(edited.getCategoryName()).isPresent()){
            note.setCategory(categoryService.getByName(edited.getCategoryName()).orElseThrow());
        }
        else {
            Category category = new Category(edited.getCategoryName());
            categoryService.save(category);
            note.setCategory(category);
        }

        note.setCategoryName(note.getCategory().getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Optional<User> user = userRepository.findByUsername(userName);
        note.setUser(user.get());
        noteService.save(note);
        return mav;
    }
    @GetMapping("/add")
    public ModelAndView addNote(@RequestParam Optional <String> newCategory){
        ModelAndView mav = new ModelAndView();
        if(newCategory.isPresent()){
            mav.setViewName("addWithNewCategory");
        }
        else {
            mav.setViewName("add");
        }
        List <Category> categoryList = categoryService.getAllCategories();

        Note note = new Note();
        mav.addObject("note",note);
        mav.addObject("categoryList",categoryList);
        mav.addObject("noteCategory",new String());
        mav.addObject("redirect","?newCategory=true");
        return mav;
    }


    @PostMapping("/add")
    public ModelAndView saveNewNote(@Valid Note note, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("redirect:/notes");

        if (bindingResult.hasErrors()) {

                 ModelAndView tmp = new ModelAndView();
                 tmp.addObject("categoryList", categoryService.getAllCategories());
                 tmp.addObject("noteCategory", note.getCategoryName());
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Optional<User> user = userRepository.findByUsername(userName);
        note.setUser(user.get());
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


    @GetMapping("/shared/{id}")
    public ModelAndView shareNote(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("shared");

        Optional<Note> note = noteService.getById(id);


        mav.addObject("title",note.get().getTitle());
        mav.addObject("www",note.get().getWww());
        mav.addObject("noteCategory",note.get().getCategory().getName());
        mav.addObject("content",note.get().getContent());
        mav.addObject("userName",note.get().getUser().getUsername());

        return mav;
    }

    @PostMapping("/shared")
    public String sharedNote(){

        return "redirect:/notes";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder)
    {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adminPage")
    public String AdminPage()
    {
        return "adminPage";
    }
}
