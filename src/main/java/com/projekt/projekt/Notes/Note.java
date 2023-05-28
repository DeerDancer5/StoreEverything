package com.projekt.projekt.Notes;
import jakarta.persistence.*;
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
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

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




