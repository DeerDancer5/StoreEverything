package com.projekt.projekt.Repositories;

import com.projekt.projekt.Notes.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    @Query("SELECT c FROM Note c WHERE c.category.name in :category_names")
    Page<Note> filterNotesByCategory(@Param("category_names")String[] category_names, Pageable pageable);

}
