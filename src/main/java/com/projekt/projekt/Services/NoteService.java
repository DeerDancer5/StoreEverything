package com.projekt.projekt.Services;

import com.projekt.projekt.Notes.Category;
import com.projekt.projekt.Repositories.CategoryRepository;
import com.projekt.projekt.Repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.projekt.projekt.Notes.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoteService {
    final
    NoteRepository noteRepository;
     final CategoryRepository categoryRepository;



    public Page<Note> getNotesPage(
           int page,
           int pageSize,
           String sortBy


    ) {

        Page<Note> notesPage = noteRepository.findAll(
        PageRequest.of(page, pageSize, Direction.ASC,sortBy));

        if(sortBy.equals("category")){
            notesPage = sortByCategoryPopularity(notesPage);
        }
        List<Note> list = notesPage.getContent();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCategoryName(categoryRepository.findById(list.get(i).getCategory().getId()).get().getName());
        }


        return notesPage;

    }
    private Page <Note> sortByCategoryPopularity(Page <Note> notesPage) {
        List<Category> categoryList = categoryRepository.findAll();
        List <Note> tmp = noteRepository.findAll();
        Collections.sort(categoryList);
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
}
