package com.projekt.projekt.Notes;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category implements Comparable<Category> {
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

    @Override
    public int compareTo(@NotNull Category o) {
        return Integer.compare(o.getNotes().size(),getNotes().size());
    }
}
