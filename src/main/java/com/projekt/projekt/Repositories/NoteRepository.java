package com.projekt.projekt.Repositories;
import com.projekt.projekt.Notes.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    @Query("SELECT c FROM Note c WHERE c.category.name in :category_names  AND c.user.username=:userName AND c.date BETWEEN :start AND :end ")
    Page<Note> filterNotesByCategory(@Param("category_names")String[] category_names,@Param("start") LocalDateTime start, @Param("end")LocalDateTime end,
                                     Pageable pageable,String userName);
    @Query("SELECT n from Note n inner join n.category c WHERE n in :filtered  AND n.user.username=:userName AND n.date BETWEEN :start AND :end order by size(c.notes) DESC")
    Page<Note> sortNotesByCategoryPopularityDesc(@Param("filtered") List <Note> filtered,@Param("start") LocalDateTime start, @Param("end")LocalDateTime end,
                                                 Pageable pageable,String userName);
    @Query("SELECT n from Note n inner join n.category c WHERE n in :filtered AND n.user.username=:userName AND n.date  BETWEEN :start AND :end order by size(c.notes) ASC ")
    Page<Note> sortNotesByCategoryPopularityAsc(@Param("filtered") List <Note> filtered,@Param("start") LocalDateTime start, @Param("end")LocalDateTime end
            ,Pageable pageable,String userName);
    @Query("SELECT c FROM Note c WHERE  c.date BETWEEN :start AND :end AND c.user.username=:userName ")
    Page<Note> filterNotesByDate(@Param("start") LocalDateTime start, @Param("end")LocalDateTime end, Pageable pageable,String userName);

}