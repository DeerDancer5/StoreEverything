package com.projekt.projekt.Notes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<Note> notes = new ArrayList<>();

    public Category() {
    }

    public Category(String name, List<Note> notes) {
        this.name = name;
        this.notes = notes;
    }
    public Category(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }
}
