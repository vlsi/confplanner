package com.github.vlsi.confplanner.solver;

import com.github.vlsi.confplanner.model.Talk;

public class TalkTopicConflict {
    private final Talk a;
    private final Talk b;
    private final long count;

    public TalkTopicConflict(Talk a, Talk b) {
        this.a = a;
        this.b = b;
        this.count = a.getTopics().stream().filter(x -> b.getTopics().contains(x)).count();
    }

    public Talk getA() {
        return a;
    }

    public Talk getB() {
        return b;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TalkTopicConflict that = (TalkTopicConflict) o;

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
        return "TalkTopicConflict{" +
                "a=" + a +
                ", b=" + b +
                ", count=" + count +
                '}';
    }
}
