package com.github.vlsi.confplanner.solver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vlsi.confplanner.model.ConferenceData;
import com.github.vlsi.confplanner.model.Room;
import com.github.vlsi.confplanner.model.RoomTimeslot;
import com.github.vlsi.confplanner.model.Talk;
import com.github.vlsi.confplanner.model.Timeslot;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.OffsetDateTime;

@PlanningEntity(difficultyComparatorClass = TalkPlacementDifficultyComparator.class,
        movableEntitySelectionFilter = TalkPlacementMoveableFilter.class)
public class TalkPlacement {
    private Talk talk;
    private boolean hasSequence;
    private boolean moveable = true;
    private RoomTimeslot roomTimeslot;

    public TalkPlacement() {
        // for planning
    }

    public TalkPlacement(Talk talk) {
        this.talk = talk;
    }

    public Talk getTalk() {
        return talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    @PlanningVariable(valueRangeProviderRefs = "roomTimeslots", strengthComparatorClass = RoomTimeslot.RoomByCapacity.class)
    public RoomTimeslot getRoomTimeslot() {
        return roomTimeslot;
    }

    public void setRoomTimeslot(RoomTimeslot roomTimeslot) {
        if (this.roomTimeslot == null) {
            System.out.println(talk + " => " + roomTimeslot);
        }
        this.roomTimeslot = roomTimeslot;
    }

    public Room getRoom() {
        if (roomTimeslot == null) {
            return null;
        }
        return roomTimeslot.getRoom();
    }

    public Timeslot getSlot() {
        if (roomTimeslot == null) {
            return null;
        }
        return roomTimeslot.getSlot();
    }

    @PlanningId
    public String getId() {
        return talk.getName();
    }

    @JsonIgnore
    public OffsetDateTime getArriveTime() {
        return talk.getArriveTime();
    }

    @JsonIgnore
    public OffsetDateTime getLeaveTime() {
        return talk.getLeaveTime();
    }

    //    @JsonIgnore
    public int getExpectedNumListeners() {
        return talk.getNumListeners();
    }

    @JsonIgnore
    public boolean isHasSequence() {
        return hasSequence;
    }

    @JsonIgnore
    public void setHasSequence(boolean hasSequence) {
        this.hasSequence = hasSequence;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
}
