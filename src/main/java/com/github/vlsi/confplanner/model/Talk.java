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
    private int numListeners;
    private String complexity;

    public Talk(@JsonProperty("language") Language language, @JsonProperty("name") String name) {
        super(name);
        this.language = language;
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
        this.arriveTime = speakers.stream().map(Speaker::getArriveTime).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null);
        this.leaveTime = speakers.stream().map(Speaker::getLeaveTime).filter(Objects::nonNull).min(Comparator.naturalOrder()).orElse(null);
        this.maxRoomSize = speakers.stream().map(Speaker::getMaxRoomSize).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null);
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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
    public int getNumListeners() {
        return numListeners == 0 ? 30 : numListeners;
    }

    public void setNumListeners(int numListeners) {
        this.numListeners = numListeners;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }
}
