package com.github.vlsi.confplanner.solver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.vlsi.confplanner.model.Talk;

public class ExpectedNumListeners {
    //    @JsonIdentityReference(alwaysAsId = true)
    @JsonUnwrapped
    private final Talk talk;
    private double count;

    @JsonCreator
    public ExpectedNumListeners(@JsonProperty("talk") Talk talk, @JsonProperty("count") double count) {
        this.talk = talk;
        this.count = count;
    }

    public static ExpectedNumListeners create(Talk talk) {
        return new ExpectedNumListeners(talk, 0);
    }

    public Talk getTalk() {
        return talk;
    }

    @JsonIgnore
    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public ExpectedNumListeners inc(double count) {
        this.count += count;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpectedNumListeners that = (ExpectedNumListeners) o;

        return talk.equals(that.talk);
    }

    @Override
    public int hashCode() {
        return talk.hashCode();
    }

    @Override
    public String toString() {
        return "ExpectedNumListeners{" +
                "talk=" + talk +
                ", count=" + count +
                '}';
    }
}
