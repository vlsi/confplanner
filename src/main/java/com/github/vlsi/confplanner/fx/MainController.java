package com.github.vlsi.confplanner.fx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vlsi.confplanner.model.*;
import com.github.vlsi.confplanner.votes.FlatVoteReader;
import com.github.vlsi.confplanner.votes.FlatVotes;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainController {
    public void parseFirstVotes(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CSV with the results of the first survey");
        fileChooser.getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("csv", "*.csv")
                );
        Node source = (Node) actionEvent.getSource();
        File file = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (file == null) {
            return;
        }


        List<FlatVotes> votes = null;
        try {
            votes = new FlatVoteReader(file.toURL()).get();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Unable to open file " + file, e);
        }

        Set<Speaker> speakers = new HashSet<>();
        Set<Talk> talks = new HashSet<>();
        for (FlatVotes vote : votes) {
            for (Talk talk : vote.getTalks()) {
                speakers.addAll(talk.getSpeakers());
            }
            talks.addAll(vote.getTalks());
        }
        List<Language> languages = talks.stream().map(Talk::getLanguage).distinct().collect(Collectors.toList());
        ConferenceData d = new ConferenceData();
        d.setCapacity(500);
        Day d1 = new Day("1", LocalDate.of(2000, 1, 1));
        d.setDays(Arrays.asList(d1));
        d.setRooms(Arrays.asList(new Room("1", 400), new Room("2", 300), new Room("3", 200)));
        d.setTimeslots(Arrays.asList(
                new Timeslot("1", d1, LocalTime.of(11, 40), 50),
                new Timeslot("2", d1, LocalTime.of(12, 50), 50),
                new Timeslot("3", d1, LocalTime.of(14, 25), 50),
                new Timeslot("4", d1, LocalTime.of(15, 35), 50),
                new Timeslot("5", d1, LocalTime.of(16, 45), 50)
        ));
        d.setSpeakers(new ArrayList<>(speakers));
        d.setTalks(new ArrayList<>(talks));
        d.setTalkPositions(Collections.emptyList());
        d.setLanguages(languages);
        d.setVotes(votes);
        d.sort();

        ObjectMapper mapper = ObjectMapperFactory.getInstance();
        String s;
        try {
            s = mapper.writeValueAsString(d);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to convert ConferenceData " + d + "to model", e);
        }

        String surveyName = file.getName();
        String outputName;
        Matcher matcher = Pattern.compile("(.*?) Формируем").matcher(surveyName);
        if (matcher.lookingAt()) {
            outputName = matcher.group(1).replaceAll(" +", "_") + ".yml";
        } else {
            outputName = surveyName.replaceAll(".csv$", ".yml");
            if (surveyName.equals(outputName)) {
                outputName += ".yml";
            }
        }
        File outputFile = new File(file.getParent(), outputName);
        if (outputFile.exists()) {
            throw new IllegalArgumentException("File " + outputName + "(" + outputFile.getAbsolutePath() + ") already exists");
        }
        if (outputFile.isDirectory()) {
            throw new IllegalArgumentException("File " + outputName + "(" + outputFile.getAbsolutePath() + ") is directory");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(outputFile.toPath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.write(s);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write YAML to " + outputFile.getAbsolutePath(), e);
        }
        System.out.println(s);
    }

    public void createSchedule(ActionEvent actionEvent) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Conference YAML info");
        fileChooser.getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("yml", "*.yml")
                );
        Node source = (Node) actionEvent.getSource();
        File file = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (file == null) {
            return;
        }

        Stage dialog = new Stage();
//        dialog.initModality(Modality.WINDOW_MODAL);

        URL resource = getClass().getResource("schedule.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();

        ScheduleController schedule = loader.getController();
        schedule.setYml(file);

        dialog.setScene(new Scene(root, 800, 600));
        dialog.setTitle(file.getName() + ", " + file.getParentFile().getAbsolutePath());
        dialog.show();
    }
}
