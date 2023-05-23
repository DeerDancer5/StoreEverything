package com.projekt.projekt.Repositories;

import com.projekt.projekt.Notes.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {


}
