package com.github.cristianocordeiro07.gra.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class WorstMovie {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studios;

    @Column
    private Boolean winner;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "WORST_MOVIE_PRODUCER", joinColumns =
    @JoinColumn(name = "WORST_MOVIE_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCER_ID"))
    private List<Producer> producers;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }
}
