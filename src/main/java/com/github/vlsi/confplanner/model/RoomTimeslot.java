package com.github.vlsi.confplanner.model;

public class RoomTimeslot {
    private final Room room;
    private final Timeslot slot;

    public RoomTimeslot(Room room, Timeslot slot) {
        this.room = room;
        this.slot = slot;
    }

    public Room getRoom() {
        return room;
    }

    public Timeslot getSlot() {
        return slot;
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
}
