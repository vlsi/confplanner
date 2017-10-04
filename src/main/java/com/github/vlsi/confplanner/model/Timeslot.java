package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@JsonIdentityInfo(scope = Timeslot.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
@JsonPropertyOrder({"name", "day", "start", "duration"})
public class Timeslot extends NamedEntity implements Comparable<Timeslot> {
    private final static Comparator<Timeslot> CMP = Comparator.comparing(Timeslot::getDay)
            .thenComparing(Timeslot::getEnd)
            .thenComparing(Timeslot::getStart);
    @JsonProperty
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime start;
    private int duration;
    @JsonIdentityReference(alwaysAsId = true)
    private Day day;

    public Timeslot(@JsonProperty("name") String name
            , @JsonProperty("day") Day day
            , @JsonProperty("start") LocalTime start
            , @JsonProperty("duration") int duration) {
        super(name);
        this.day = day;
        this.start = start;
        this.duration = duration;
    }

    public Day getDay() {
        return day;
    }

    public LocalTime getStart() {
        return start;
    }

    @JsonIgnore
    public LocalTime getEnd() {
        return start.plus(duration, ChronoUnit.MINUTES);
    }

    @JsonIgnore
    public OffsetDateTime getStartTimestamp() {
        return OffsetDateTime.of(day.getDate(), start, ZoneOffset.ofHours(3));
    }

    @JsonIgnore
    public OffsetDateTime getEndTimestamp() {
        return OffsetDateTime.of(day.getDate(), start.plus(duration, ChronoUnit.MINUTES), ZoneOffset.ofHours(3));
    }

    public Duration minus(Timeslot o) {
        return Duration.between(o.getEndTimestamp(), getStartTimestamp());
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(Timeslot o) {
        return CMP.compare(this, o);
    }

    @Override
    public String toString() {
        return "Slot{" + day.getDate() + " " + start +
                '}';
    }
}
