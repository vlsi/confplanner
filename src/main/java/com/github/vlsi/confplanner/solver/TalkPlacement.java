package com.github.vlsi.confplanner.solver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vlsi.confplanner.model.Room;
import com.github.vlsi.confplanner.model.Talk;
import com.github.vlsi.confplanner.model.Timeslot;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicLong;

@PlanningEntity(difficultyComparatorClass = TalkPlacementDifficultyComparator.class,
        movableEntitySelectionFilter = TalkPlacementMoveableFilter.class)
public class TalkPlacement {
    private static AtomicLong IG_GENERATOR = new AtomicLong();
    @JsonIgnore
    private Long id;
    private Talk talk;
    private Room room;
    private Timeslot slot;
    private boolean hasSequence;
    private boolean moveable = true;

    public TalkPlacement() {
        // for planning
    }

    public TalkPlacement(Talk talk) {
        this.id = IG_GENERATOR.getAndIncrement();
        this.talk = talk;
    }

    public Talk getTalk() {
        return talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    @PlanningVariable(valueRangeProviderRefs = "roomsProvider", strengthComparatorClass = Room.RoomByCapacity.class)
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @PlanningVariable(valueRangeProviderRefs = "timeslotsProvider")
    public Timeslot getSlot() {
        return slot;
    }

    public void setSlot(Timeslot slot) {
        this.slot = slot;
    }

    @PlanningId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
