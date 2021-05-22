package com.github.cristianocordeiro07.gra.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producer {

    public Producer() {

    }

    public Producer(String name) {
        this.name = name;
    }

    @Id
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "producers", fetch = FetchType.LAZY)
    private Set<WorstMovie> movies = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WorstMovie> getMovies() {
        return movies;
    }

    public void setMovies(Set<WorstMovie> movies) {
        this.movies = movies;
    }
}
