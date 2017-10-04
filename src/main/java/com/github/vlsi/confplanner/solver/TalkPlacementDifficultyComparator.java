package com.github.vlsi.confplanner.solver;

import java.util.Comparator;


public class TalkPlacementDifficultyComparator implements Comparator<TalkPlacement> {
    private static final Comparator<TalkPlacement> CMP =
            Comparator.comparing(TalkPlacement::isHasSequence, Comparator.naturalOrder())
                    .thenComparing(TalkPlacement::getArriveTime, Comparator.nullsFirst(Comparator.naturalOrder()))
                    .thenComparing(TalkPlacement::getLeaveTime, Comparator.nullsFirst(Comparator.reverseOrder()))
//                    .thenComparing(TalkPlacement::getMaxRoomSize, Comparator.nullsFirst(Comparator.naturalOrder()))
                    .thenComparing(TalkPlacement::getExpectedNumListeners)
                    .thenComparing(TalkPlacement::getId);

    @Override
    public int compare(TalkPlacement o1, TalkPlacement o2) {
        return CMP.compare(o1, o2);
    }
}
