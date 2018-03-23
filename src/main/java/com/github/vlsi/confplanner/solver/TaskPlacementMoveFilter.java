package com.github.vlsi.confplanner.solver;

import com.github.vlsi.confplanner.model.ConferenceData;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.heuristic.selector.move.generic.SwapMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.Objects;

public class TaskPlacementMoveFilter implements SelectionFilter<ConferenceData, SwapMove> {
    @Override
    public boolean accept(ScoreDirector<ConferenceData> scoreDirector, SwapMove selection) {
        TalkPlacement leftEntity = (TalkPlacement) selection.getLeftEntity();
        TalkPlacement rightEntity = (TalkPlacement) selection.getRightEntity();
        return Objects.equals(leftEntity.getTalk().getRoom(), rightEntity.getTalk().getRoom());
    }
}
