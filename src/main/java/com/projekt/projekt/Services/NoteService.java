package com.projekt.projekt.Services;
import com.projekt.projekt.Repositories.CategoryRepository;
import com.projekt.projekt.Repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.projekt.projekt.Notes.Note;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


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
           LocalDate startDate,
           LocalDate endDate
    ) {
        Direction d = Direction.ASC;
        if(sortDir.equals("desc")) {
            d = Direction.DESC;
        }
        Page <Note> notesPage = getResults(category,page,pageSize,d,sortBy.toLowerCase(),sortDir,startDate,endDate);
        updateCategoryNames(notesPage);
        return notesPage;

    }

    private void updateCategoryNames(Page <Note> notesPage) {
        List<Note> list = notesPage.getContent();
        for (Note note : list) {
            note.setCategoryName(categoryRepository.findById(note.getCategory().getId()).get().getName());
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
    private Page <Note> getResults(String category,int page,int pageSize,Direction d,String sortBy,String sortDir,LocalDate startDate,
                                   LocalDate endDate){

        Pageable pageable = PageRequest.of(page,pageSize,d,sortBy);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23,59,59);
        Page<Note> notesPage = noteRepository.filterNotesByDate(start,end,pageable);
        String split[];

        if(category.length()>0) {

            split = category.split(",");
            notesPage=noteRepository.filterNotesByCategory(split,start,end,pageable);
        }

        if(sortBy.equals("category")) {
            List<Note> filtered = notesPage.getContent();

            if(sortDir.toLowerCase().equals("asc")) {
                notesPage = noteRepository.sortNotesByCategoryPopularityAsc(filtered,start,end,pageable);
            }

            else if(sortDir.toLowerCase().equals("desc")) {
                notesPage = noteRepository.sortNotesByCategoryPopularityDesc(filtered,start,end,pageable);
            }
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
