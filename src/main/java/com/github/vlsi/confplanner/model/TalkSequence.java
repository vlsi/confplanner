package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TalkSequence {
    @JsonIdentityReference(alwaysAsId = true)
    private final Talk first;
    @JsonIdentityReference(alwaysAsId = true)
    private final Talk second;
    @JsonProperty(defaultValue = "ADJACENT")
    private final Type type;
    public TalkSequence(@JsonProperty("first") Talk first, @JsonProperty("second") Talk second
            , @JsonProperty("type") Type type) {
        this.first = first;
        this.second = second;
        this.type = type;
    }

    public Talk getFirst() {
        return first;
    }

    public Talk getSecond() {
        return second;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ADJACENT,
        SOFT,
        CONFLICTS,
    }
}
