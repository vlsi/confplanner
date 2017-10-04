package com.github.vlsi.confplanner.solver;

public class ScoringParams {
    private final int roomCapacityFactor;

    public ScoringParams(int roomCapacityFactor) {
        this.roomCapacityFactor = roomCapacityFactor;
    }

    public int getRoomCapacityFactor() {
        return roomCapacityFactor;
    }
}
