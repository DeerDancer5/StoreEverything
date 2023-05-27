package com.projekt.projekt.Services;

import com.projekt.projekt.Repositories.CategoryRepository;
import com.projekt.projekt.Repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.projekt.projekt.Notes.Note;

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

        Page <Note> notesPage = noteRepository.findAll(
                PageRequest.of(page,pageSize, Direction.ASC,sortBy));

        List<Note> lista = notesPage.getContent();
        for(int i=0;i< lista.size();i++){
            lista.get(i).setCategoryName(categoryRepository.findById(lista.get(i).getCategory().getId()).get().getName());
        }

        return notesPage;

    }
}
