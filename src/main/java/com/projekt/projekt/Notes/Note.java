package com.projekt.projekt.Notes;

import java.time.LocalDateTime;

public class Note {
    private String title;
    private String category;
    private String content;
    private LocalDateTime date;

    public Note(String title, String category, String content, LocalDateTime date) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
