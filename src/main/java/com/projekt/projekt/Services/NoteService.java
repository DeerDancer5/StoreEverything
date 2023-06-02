package com.projekt.projekt.Services;
import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Repositories.CategoryRepository;
import com.projekt.projekt.Repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.projekt.projekt.Notes.Note;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NoteService {
     final NoteRepository noteRepository;
     final CategoryRepository categoryRepository;


    public Page<Note> getNotesPage(
           int page,
           int pageSize,
           String sortBy,
           String sortDir,
           String category,
           String startDate,
           String endDate
    ) {
        Direction d = Direction.ASC;
        if(sortDir.equals("desc")) {
            d = Direction.DESC;
        }
        Page <Note> notesPage = getResults(category,page,pageSize,d,sortBy.toLowerCase(),sortDir,startDate,endDate);
        updateCategoryNames(notesPage);
        return notesPage;

    }
    private Page <Note> sortByCategoryPopularity(Page <Note> notesPage, String sortDir) {
        List<Category> categoryList = categoryRepository.findAll();
        List <Note> tmp = notesPage.getContent();
        Collections.sort(categoryList);
        if (sortDir.equals("desc")) {
            Collections.reverse(categoryList);
        }

        List <Note> sorted = new ArrayList<>();
        for(int i=0;i<categoryList.size();i++) {
            for(int j=0;j<tmp.size();j++) {
                if(tmp.get(j).getCategory().equals(categoryList.get(i))) {
                    sorted.add(tmp.get(j));
                }
            }
        }
        Pageable pageable=notesPage.getPageable();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), sorted.size());
        notesPage = new PageImpl<>(sorted.subList(start, end), pageable, sorted.size());
        return notesPage;
    }
    private void updateCategoryNames(Page <Note> notesPage) {
        List<Note> list = notesPage.getContent();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCategoryName(categoryRepository.findById(list.get(i).getCategory().getId()).get().getName());
        }
    }
    public List <String> getSortOptions(){
        List <String> options = new ArrayList<>();
        options.add("--");
        options.add("Date");
        options.add("Title");
        options.add("Category");
        return options;
    }
    public List <Integer> getSizeOptions(){
        List <Integer> sizes = new ArrayList<>();
        sizes.add(2);
        sizes.add(4);
        sizes.add(6);
        sizes.add(10);
        return sizes;
    }
    private Page <Note> getResults(String category,int page,int pageSize,Direction d,String sortBy,String sortDir,String startDate,
                                   String endDate){
        Page<Note> notesPage;
        Pageable pageable = PageRequest.of(page,pageSize,d,sortBy);
        LocalDateTime start = dateFromString(startDate);
        LocalDateTime end = dateFromString(endDate);
        notesPage = noteRepository.filterNotesByDate(start,end,pageable);
        if(category.length()>0) {

            String [] split = category.split(",");
            notesPage=noteRepository.filterNotesByCategory(split,pageable);
        }
        else {
            notesPage = noteRepository.findAll(
                    PageRequest.of(page, pageSize, d,sortBy));

        }
        if(sortBy.equals("category")){
            notesPage = sortByCategoryPopularity(notesPage,sortDir);

        }



        return notesPage;
    }
    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }
    public void save(Note note){
        noteRepository.save(note);
    }
    public String getEarliestDate() {
        List <Note> list = noteRepository.findAll();
        Collections.sort(list,
                (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        String date = (list.get(0).dateToString());
        return date;
    }
    public void delete(Note note) {
        noteRepository.delete(note);
    }
    public LocalDateTime dateFromString(String date) {
        String [] split = date.split("-");
        LocalDate tmp = LocalDate.of(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
        LocalDateTime output = tmp.atStartOfDay();
        return output;
    }

}
