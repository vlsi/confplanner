package com.github.vlsi.confplanner.fx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.*;

public class ScheduleController {
    public WebView results;
    public Spinner duration;
    public Button start;
    public ProgressBar progress;
    public Label statusLabel;
    public Spinner capacityFactor;

    private File yml;

    private ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Process " + yml.getAbsolutePath());
            t.setDaemon(true);
            return t;
        }
    });
    private SolverAction currentAction;
    private Future<Boolean> currentResult;
    private String prevDescription;
    private Timeline statusUpdate = new Timeline(new KeyFrame(Duration.seconds(1),
            ae -> updateStatus()));

    public void setYml(File yml) {
        this.yml = yml;
    }

    public synchronized void startStop(ActionEvent event) {
        if (currentResult != null && !currentResult.isDone()) {
            // stop
            currentAction.cancel();
            return;
        }
        start.setText("Stop");
        currentAction = new SolverAction(yml, ((Number) duration.getValue()).intValue(), ((Number) capacityFactor.getValue()).intValue());
        statusUpdate.setCycleCount(Animation.INDEFINITE);
        statusUpdate.play();
        currentResult = executor.submit(currentAction);
    }

    public void updateStatus() {
        System.out.println("results = " + results);
        Future<Boolean> currentResult = this.currentResult;
        boolean done = currentResult != null && currentResult.isDone();
        if (done) {
            statusUpdate.stop();
            start.setText("Start");
            try {
                currentResult.get(1, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                cause.printStackTrace();
                String msg = cause.getMessage();
                if (msg.length() > 200) {
                    msg = msg.substring(0, 200);
                }
                statusLabel.setText("Failed: " + msg);
                StringWriter sw = new StringWriter();
                cause.printStackTrace(new PrintWriter(sw));
                results.getEngine().loadContent(sw.toString(), "text/plain");
                return;
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        SolverAction action = this.currentAction;
        double progress = action.getProgress();
        this.progress.setProgress(progress);
        statusLabel.setText(done ? "Done" : action.getShortStatus());
        String descr = action.describeBest();
        if (prevDescription == descr) {
            return;
        }
        prevDescription = descr;
        results.getEngine().loadContent(descr, "text/html");

    }
}
