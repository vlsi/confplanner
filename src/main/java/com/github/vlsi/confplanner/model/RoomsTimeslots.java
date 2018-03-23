package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RoomsTimeslots {
    private final List<Room> rooms;
    @JsonAlias("timeslot")
    @JsonIdentityReference(alwaysAsId = true)
    private final List<Timeslot> timeslots;

    public RoomsTimeslots(@JsonProperty("rooms") List<Room> rooms, @JsonProperty("timeslots") List<Timeslot> timeslots) {
        this.rooms = rooms;
        this.timeslots = timeslots;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomsTimeslots that = (RoomsTimeslots) o;

        if (!rooms.equals(that.rooms)) return false;
        return timeslots.equals(that.timeslots);
    }

    @Override
    public int hashCode() {
        int result = rooms.hashCode();
        result = 31 * result + timeslots.hashCode();
        return result;
    }
}
