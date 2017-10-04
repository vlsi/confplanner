package com.github.vlsi.confplanner.fx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vlsi.confplanner.model.ConferenceData;
import com.github.vlsi.confplanner.model.ObjectMapperFactory;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;

public class SolverAction implements Callable<Boolean> {
    private static final String SOLVER_CONFIG = "com/github/vlsi/confplanner/solver/confScheduleSolverConfig.xml";

    private final File yml;
    private final int duration;
    private final int capacityFactor;
    private volatile Solver<ConferenceData> solver;
    private volatile ConferenceData prevBest;
    private volatile String prevBestDescr;

    public SolverAction(File yml, int duration, int capacityFactor) {
        this.yml = yml;
        this.duration = duration == 0 ? 1 : duration;
        this.capacityFactor = capacityFactor;
    }

    @Override
    public Boolean call() throws Exception {
        ObjectMapper mapper = ObjectMapperFactory.getInstance();
        ConferenceData data;
        data = mapper.readValue(yml, ConferenceData.class);
        data.setRoomCapacityFactor(capacityFactor);
        SolverFactory<ConferenceData> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        solverFactory.getSolverConfig().getTerminationConfig().setSecondsSpentLimit((long) duration);
        solver = solverFactory.buildSolver();
        solver.solve(data);
        System.out.println("solver.getBestScore() = " + solver.getBestScore());
        return true;
    }

    public void cancel() {
        Solver<ConferenceData> solver = this.solver;
        if (solver != null && !solver.isSolving()) {
            solver.terminateEarly();
        }
    }

    public double getProgress() {
        Solver<ConferenceData> solver = this.solver;
        return solver == null ? 0 : (solver.getTimeMillisSpent() * 0.001 / duration);
    }

    public String getShortStatus() {
        Solver<ConferenceData> solver = this.solver;
        String status = solver == null || !solver.isSolving() ? "idle" : "solving";
        if (solver != null) {
            status += ", best score " + solver.getBestScore();
        }
        return status;
    }

    public String describeBest() {
        Solver<ConferenceData> solver = this.solver;
        if (solver == null) {
            return "";
        }
        ConferenceData currentBest = solver.getBestSolution();
        if (currentBest == prevBest) {
            return prevBestDescr;
        }
        prevBest = currentBest;
        currentBest.sort();

        StringWriter sw = new StringWriter();

        ScoreDirector<ConferenceData> scorer = solver.getScoreDirectorFactory().buildScoreDirector();
        scorer.setWorkingSolution(currentBest);
        Collection<ConstraintMatchTotal> totals = scorer.getConstraintMatchTotals();
        Map<Object, Indictment> map = scorer.getIndictmentMap();

        sw.append("<html><body style='font-family:monospace'>");
        for (ConstraintMatchTotal total : totals) {
            sw.append(String.valueOf(total.getScoreTotal())).append("\t").append(total.getConstraintName()).append("<br>");
        }
        sw.append("<br>");
        currentBest.print(sw, map);
        sw.append("</body></html>");
        prevBestDescr = sw.toString();
        return prevBestDescr;
    }
}
