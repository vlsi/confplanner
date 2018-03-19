package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.OffsetDateTime;

@JsonIdentityInfo(scope = Speaker.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Speaker extends NamedEntity implements Comparable<Speaker> {
    private Integer maxRoomSize;
    private Integer minRoomSize;
    private OffsetDateTime arriveTime;
    private OffsetDateTime leaveTime;

    public Speaker(@JsonProperty("name") String name) {
        super(name);
    }

    public OffsetDateTime getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(OffsetDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    public OffsetDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(OffsetDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Integer getMaxRoomSize() {
        return maxRoomSize;
    }

    public void setMaxRoomSize(Integer maxRoomSize) {
        this.maxRoomSize = maxRoomSize;
    }

    public Integer getMinRoomSize() {
        return minRoomSize;
    }

    public void setMinRoomSize(Integer minRoomSize) {
        this.minRoomSize = minRoomSize;
    }

    @Override
    public int compareTo(Speaker o) {
        return getName().compareTo(o.getName());
    }
}
