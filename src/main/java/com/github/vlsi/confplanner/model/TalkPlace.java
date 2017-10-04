package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TalkPlace {
    @JsonIdentityReference(alwaysAsId = true)
    private final Talk talk;
    @JsonIdentityReference(alwaysAsId = true)
    private final Timeslot slot;
    @JsonIdentityReference(alwaysAsId = true)
    private final Room room;

    public TalkPlace(@JsonProperty("talk") Talk talk, @JsonProperty("slot") Timeslot slot, @JsonProperty("room") Room room) {
        this.talk = talk;
        this.slot = slot;
        this.room = room;
    }

    public Talk getTalk() {
        return talk;
    }

    public Timeslot getSlot() {
        return slot;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "TalkPlace{" +
                "talk=" + talk +
                ", slot=" + slot +
                ", room=" + room +
                '}';
    }
}
