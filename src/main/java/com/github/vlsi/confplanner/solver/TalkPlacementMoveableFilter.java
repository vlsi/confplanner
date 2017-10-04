package com.github.vlsi.confplanner.solver;

import com.github.vlsi.confplanner.model.ConferenceData;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.score.director.ScoreDirector;

public class TalkPlacementMoveableFilter implements SelectionFilter<ConferenceData, TalkPlacement> {
    @Override
    public boolean accept(ScoreDirector<ConferenceData> scoreDirector, TalkPlacement selection) {
        return selection.isMoveable();
    }
}
