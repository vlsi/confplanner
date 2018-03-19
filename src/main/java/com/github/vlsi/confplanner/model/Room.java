package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Comparator;
import java.util.List;

@JsonIdentityInfo(scope = Room.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Room extends NamedEntity implements Comparable<Room> {
    private final int capacity;
    @JsonIdentityReference(alwaysAsId = true)
    private List<Timeslot> availableTimeslots;
    @JsonIdentityReference(alwaysAsId = true)
    private List<Timeslot> unavailableTimeslots;
    @JsonProperty(defaultValue = "false")
    private boolean inviteOnly;

    public Room(@JsonProperty("name") String name, @JsonProperty("capacity") int capacity,
                @JsonProperty("inviteOnly") boolean inviteOnly) {
        super(name);
//        this.name = name;
        this.capacity = capacity;
        this.inviteOnly = inviteOnly;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Timeslot> getAvailableTimeslots() {
        return availableTimeslots;
    }

    public void setAvailableTimeslots(List<Timeslot> availableTimeslots) {
        this.availableTimeslots = availableTimeslots;
    }

    public List<Timeslot> getUnavailableTimeslots() {
        return unavailableTimeslots;
    }

    public void setUnavailableTimeslots(List<Timeslot> unavailableTimeslots) {
        this.unavailableTimeslots = unavailableTimeslots;
    }

    @JsonIgnore
    public boolean isInviteOnly() {
        return inviteOnly;
    }

    public void setInviteOnly(boolean inviteOnly) {
        this.inviteOnly = inviteOnly;
    }

    @Override
    public int compareTo(Room o) {
        return getName().compareTo(o.getName());
    }

    public static class RoomByCapacity implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            return Integer.compare(o1.capacity, o2.capacity);
        }
    }
}
