package com.projekt.projekt.Services;

import com.projekt.projekt.Repositories.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.projekt.projekt.Notes.Note;


@Service
public class NoteService {
    final
    NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Page<Note> getNotesPage(
           int page,
           int pageSize,
           String sortBy


    ) {
        return noteRepository.findAll(
                PageRequest.of(page,pageSize, Sort.Direction.ASC,sortBy)
        );
    }
}
