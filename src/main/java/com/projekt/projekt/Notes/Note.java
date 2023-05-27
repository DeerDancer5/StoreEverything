package com.projekt.projekt.Notes;


import com.projekt.projekt.Services.CategoryService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
@Entity
@Table(name="note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String categoryName;
    private String content;
    private LocalDateTime adddate;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Note(String title, String content, LocalDateTime adddate, Category category) {
        this.title = title;
        this.content = content;
        this.adddate = adddate;
        this.category = category;
    }

    public Note() {

    }

    public String getTitle() {
        return title;
    }



    public String getContent() {
        return content;
    }

    public String dateToString() {
        String tmp = adddate.getYear() + "-";
        String month = Integer.toString(adddate.getMonthValue());
        if (adddate.getMonthValue() < 10) {
            month = "0" + month;
        }
        tmp = tmp + month + "-" + adddate.getDayOfMonth();
        return tmp;
    }

    public Long getId() {
        return id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category getCategory() {
        return category;
    }
}




