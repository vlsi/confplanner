package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RoomTimeslots {
    private final Room room;
    @JsonAlias("timeslot")
    @JsonIdentityReference(alwaysAsId = true)
    private final List<Timeslot> timeslots;

    public RoomTimeslots(@JsonProperty("room") Room room, @JsonProperty("timeslots") List<Timeslot> timeslots) {
        this.room = room;
        this.timeslots = timeslots;
    }

    public Room getRoom() {
        return room;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomTimeslots that = (RoomTimeslots) o;

        if (!room.equals(that.room)) return false;
        return timeslots.equals(that.timeslots);
    }

    @Override
    public int hashCode() {
        int result = room.hashCode();
        result = 31 * result + timeslots.hashCode();
        return result;
    }
}
