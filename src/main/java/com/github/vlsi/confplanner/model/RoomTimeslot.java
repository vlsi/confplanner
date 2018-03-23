package com.github.vlsi.confplanner.model;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import java.util.Comparator;

public class RoomTimeslot {
    public static class RoomByCapacity implements Comparator<RoomTimeslot> {
        @Override
        public int compare(RoomTimeslot o1, RoomTimeslot o2) {
            return Integer.compare(o1.getRoom().getCapacity(), o2.getRoom().getCapacity());
        }
    }

    private final Room room;
    private final Timeslot slot;
    private final String id;

    public RoomTimeslot(Room room, Timeslot slot) {
        this.room = room;
        this.slot = slot;
        this.id = room.getName() + "/" + slot.getName();
    }

    public Room getRoom() {
        return room;
    }

    public Timeslot getSlot() {
        return slot;
    }

    @PlanningId
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomTimeslot that = (RoomTimeslot) o;

        if (!room.equals(that.room)) return false;
        return slot.equals(that.slot);
    }

    @Override
    public int hashCode() {
        int result = room.hashCode();
        result = 31 * result + slot.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RoomTimeslot{" +
                "room=" + room +
                ", slot=" + slot +
                '}';
    }
}
