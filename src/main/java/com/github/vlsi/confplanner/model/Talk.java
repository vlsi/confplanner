package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.*;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(scope = Talk.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
@JsonPropertyOrder({"name", "language", "speakers"})
public class Talk extends NamedEntity {
    @JsonAlias("speaker")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Speaker> speakers;

    @JsonIdentityReference(alwaysAsId = true)
    private Language language;

    @JsonAlias("topic")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Topic> topics;

    private OffsetDateTime arriveTime;
    private OffsetDateTime leaveTime;
    private Integer maxRoomSize;
    private Integer minRoomSize;
    @JsonProperty
    private int numListeners;
    private String complexity;
    private boolean ignore;
    @JsonProperty
    private Room room;

    public Talk(@JsonProperty("language") Language language, @JsonProperty("name") String name,
                @JsonProperty("room") Room room) {
        super(name);
        this.language = language;
        this.room = room;
    }

    @JsonIgnore
    public String getFullTitle() {
        StringBuilder sb = new StringBuilder();
        for (Speaker speaker : speakers) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(speaker.getName());
        }
        sb.append(" - ");
        sb.append(getName());
        return sb.toString();
    }

    public Language getLanguage() {
        return language;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
        if (arriveTime == null) {
            this.arriveTime = speakers.stream().map(Speaker::getArriveTime).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null);
        }
        if (leaveTime == null) {
            this.leaveTime = speakers.stream().map(Speaker::getLeaveTime).filter(Objects::nonNull).min(Comparator.naturalOrder()).orElse(null);
        }
        if (maxRoomSize == null) {
            this.maxRoomSize = speakers.stream().map(Speaker::getMaxRoomSize).filter(Objects::nonNull).min(Comparator.naturalOrder()).orElse(null);
        }
        if (minRoomSize == null) {
            this.minRoomSize = speakers.stream().map(Speaker::getMinRoomSize).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null);
        }
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @JsonProperty
    public void setArriveTime(OffsetDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    @JsonProperty
    public void setLeaveTime(OffsetDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    @JsonProperty
    public void setMaxRoomSize(Integer maxRoomSize) {
        this.maxRoomSize = maxRoomSize;
    }

    @JsonProperty
    public void setMinRoomSize(Integer minRoomSize) {
        this.minRoomSize = minRoomSize;
    }

    @JsonIgnore
    public OffsetDateTime getArriveTime() {
        return arriveTime;
    }

    @JsonIgnore
    public OffsetDateTime getLeaveTime() {
        return leaveTime;
    }

    @JsonIgnore
    public Integer getMaxRoomSize() {
        return maxRoomSize;
    }

    @JsonIgnore
    public Integer getMinRoomSize() {
        return minRoomSize;
    }

    @JsonIgnore
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @JsonIgnore
    public int getNumListeners() {
        return numListeners == 0 ? 30 : numListeners;
    }

    @JsonProperty
    public void setNumListeners(int numListeners) {
        this.numListeners = numListeners;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    @JsonIgnore
    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}
