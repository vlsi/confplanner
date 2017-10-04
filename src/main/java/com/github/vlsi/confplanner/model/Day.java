package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;

@JsonIdentityInfo(scope = Day.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Day implements Comparable<Day> {
    private final String name;
    private final LocalDate date;

    public Day(@JsonProperty("name") String name, @JsonProperty("date") LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(Day o) {
        return date.compareTo(o.date);
    }
}
