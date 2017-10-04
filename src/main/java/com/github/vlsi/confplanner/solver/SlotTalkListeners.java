package com.github.vlsi.confplanner.solver;

import com.github.vlsi.confplanner.model.Timeslot;

public class SlotTalkListeners {
    private final Timeslot slot;
    private final double count;

    public SlotTalkListeners(Timeslot slot, double count) {
        this.slot = slot;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlotTalkListeners that = (SlotTalkListeners) o;

        if (Double.compare(that.count, count) != 0) return false;
        return slot.equals(that.slot);
    }

    public Timeslot getSlot() {
        return slot;
    }

    public double getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = slot.hashCode();
        temp = Double.doubleToLongBits(count);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SlotTalkListeners{" +
                "slot=" + slot +
                ", count=" + count +
                '}';
    }
}
