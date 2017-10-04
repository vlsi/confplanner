package com.github.vlsi.confplanner.solver;

import com.github.vlsi.confplanner.model.Talk;

public class TalkSpeakerConflict {
    private final Talk a;
    private final Talk b;

    public TalkSpeakerConflict(Talk a, Talk b) {
        this.a = a;
        this.b = b;
    }

    public Talk getA() {
        return a;
    }

    public Talk getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TalkSpeakerConflict that = (TalkSpeakerConflict) o;

        if (!a.equals(that.a)) return false;
        return b.equals(that.b);
    }

    @Override
    public int hashCode() {
        int result = a.hashCode();
        result = 31 * result + b.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TalkSpeakerConflict{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
