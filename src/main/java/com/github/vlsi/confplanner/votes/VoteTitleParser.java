package com.github.vlsi.confplanner.votes;

import com.github.vlsi.confplanner.model.Language;
import com.github.vlsi.confplanner.model.SpaceCleaner;
import com.github.vlsi.confplanner.model.Speaker;
import com.github.vlsi.confplanner.model.Talk;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoteTitleParser {
    private Pattern pattern = Pattern.compile("(.*) [—-] (.*)[ ]\\[(\\d+)/(\\w+)\\]");
    private SpaceCleaner cleaner = new SpaceCleaner();

    public Talk parse(String line) {
        String val = cleaner.clean(line);
        Matcher matcher = pattern.matcher(val);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unable to parse line <<" + line + ">>");
        }
        String title = matcher.group(1);
        if (title.startsWith("«") && title.endsWith("»")) {
            title = title.substring(1, title.length() - 2);
        }
        String speakersStr = matcher.group(2);
        String[] speakers = speakersStr.split(", ");
        List<Speaker> speakerList = new ArrayList<>();
        for (int i = 0; i < speakers.length / 2; i++) {
            String speaker = speakers[i];
            speakerList.add(new Speaker(speaker));
        }
        String complexity = matcher.group(3);
        String language = matcher.group(4).toLowerCase();
        Talk talk = new Talk(new Language(language), title);
        talk.setSpeakers(speakerList);
        talk.setComplexity(complexity);
        return talk;
    }
}
