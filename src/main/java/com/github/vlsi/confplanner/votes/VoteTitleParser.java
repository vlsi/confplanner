package com.github.vlsi.confplanner.votes;

import com.github.vlsi.confplanner.model.Language;
import com.github.vlsi.confplanner.model.Room;
import com.github.vlsi.confplanner.model.SpaceCleaner;
import com.github.vlsi.confplanner.model.Speaker;
import com.github.vlsi.confplanner.model.Talk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoteTitleParser {
    private Pattern pattern = Pattern.compile("(.*) *[—-] *(.*?)[ ]*\\[(\\d+)/(\\w+)\\](\\(.*)?");
    private SpaceCleaner cleaner = new SpaceCleaner();

    public Talk parse(String line) {
        String val = cleaner.clean(line);
        Matcher matcher = pattern.matcher(val);
        if (!matcher.matches()) {
            if ("Кейноут: Juergen Hoeller —Spring Framework 5.0 on JDK 8 & 9".equals(val)) {
                Talk talk = new Talk(new Language("en"), "Spring Framework 5.0 on JDK 8 & 9", null);
                talk.setSpeakers(Arrays.asList(new Speaker("Juergen Hoeller")));
                return talk;
            }
            if ("Кейноут: Михаил Гельфанд — Большие данные в современной биологии".equals(val)) {
                Talk talk = new Talk(new Language("ru"), "Большие данные в современной биологии", null);
                talk.setSpeakers(Arrays.asList(new Speaker("Михаил Гельфанд")));
                return talk;
            }
            if (val.contains("Приключения Сеньора Холмса и Джуниора Ватсона в мире разработки ПО")) {
                Talk talk = new Talk(new Language("ru"), "Приключения Сеньора Холмса и Джуниора Ватсона в мире разработки ПО", null);
                talk.setSpeakers(Arrays.asList(new Speaker("Барух Садогурский"), new Speaker("Евгений Борисов")));
                return talk;
            }
            throw new IllegalArgumentException("Unable to parse line <<" + line + ">>");
        }
        String title = matcher.group(1).trim();
        if (title.startsWith("«") && title.endsWith("»")) {
            title = title.substring(1, title.length() - 1);
        }
        title = title.trim();
        String speakersStr = matcher.group(2);
        String[] speakers = speakersStr.split(", ");
        List<Speaker> speakerList = new ArrayList<>();
        if (speakers.length == 2 && speakers[0].contains(" / ")) {
            speakers = speakers[0].split(" / ");
        } else {
            speakers = new String[]{speakers[0]};
        }
        for (int i = 0; i < speakers.length; i++) {
            String speaker = speakers[i];
            speakerList.add(new Speaker(speaker));
        }
        String complexity = matcher.group(3);
        String language = matcher.group(4).toLowerCase();
        if (language != null && language.length() > 2) {
            language = language.substring(0, 2);
        }
        Talk talk = new Talk(new Language(language), title, null);
        talk.setSpeakers(speakerList);
        talk.setComplexity(complexity);
        return talk;
    }
}
