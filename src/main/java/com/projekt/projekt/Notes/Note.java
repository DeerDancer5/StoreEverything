package com.projekt.projekt.Notes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


@Entity
@Table(name="note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title can't be empty")
    @Size(min=2, max=50, message = "Title must be between 2 and 50 characters")
    private String title;
    @NotBlank(message = "Category can't be empty")
    @Size(min=2, max=50, message = "Category must be between 2 and 50 characters")
    private String categoryName;
    @NotBlank(message = "Content can't be empty")
    @Size(min=2, max=500, message = "Content must be between 2 and 500 characters")
    private String content;
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Note(String title, String content, LocalDateTime date, Category category) {
        this.title = title;
        this.content = content;
        this.date = date;
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
        String tmp = date.getYear() + "-";
        String month = Integer.toString(date.getMonthValue());
        if (date.getMonthValue() < 10) {
            month = "0" + month;
        }
        tmp = tmp + month + "-" + date.getDayOfMonth();
        return tmp;
    }

    public LocalDateTime getDate() {
        return date;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setContent(String content) {
        this.content = content;
    }
}




