package com.projekt.projekt.Services;

import com.projekt.projekt.Repositories.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.projekt.projekt.Notes.Note;

import java.util.Optional;

@Service
public class NoteService {
    final
    NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Page<Note> getAllNotes(
            Optional<Integer> page,
            Optional<String> sortBy


    ) {
        return noteRepository.findAll(
                PageRequest.of(page.orElse(0),5, Sort.Direction.ASC,sortBy.orElse("id"))
        );
    }
}
