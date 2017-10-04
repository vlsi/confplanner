package com.github.vlsi.confplanner.votes;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.github.vlsi.confplanner.model.Talk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FlatVoteReader implements Supplier<List<FlatVotes>> {
    private final URL in;

    public FlatVoteReader(URL in) {
        this.in = in;
    }

    @Override
    public List<FlatVotes> get() {
        Map<FlatVotes, FlatVotes> voteMap = new HashMap<>();
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        try {
            VoteTitleParser p = new VoteTitleParser();
            MappingIterator<Object[]> it = mapper.readerFor(Object[].class).readValues(in);
            it.next();
            it.next();
            while (it.hasNext()) {
                Object[] value = it.next();
                List<Talk> selectedTalks = new ArrayList<>();
                for (int i = 7; i < value.length; i++) {
                    Object o = value[i];
                    if (!(o instanceof String)) {
                        continue;
                    }
                    String s = (String) o;
                    if (s.isEmpty()) {
                        continue;
                    }
                    Talk talk = p.parse(s);
                    selectedTalks.add(talk);
                }
                FlatVotes key = new FlatVotes(selectedTalks);
                if (voteMap.containsKey(key)) {
                    key = voteMap.get(key);
                    key.setCount(key.getCount() + 1);
                } else {
                    voteMap.put(key, key);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to parse " + in);
        }
        List<FlatVotes> votes = voteMap.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

//        List<FlatVotes> votes = new ArrayList<>();
//        try (InputStream is = in;
//             BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
//            br.readLine(); // ignore header
//
//            String line;
//            line = br.readLine();
//            line = br.readLine();
//            Pattern topic = Pattern.compile("(?:\"[^\"]+\"|[^,]+)");
//            Pattern speakerTitle = Pattern.compile("([^—-]*) [—-] (.*)");
//            while ((line = br.readLine()) != null) {
//                Matcher matcher = topic.matcher(line);
//                List<Talk> selectedTalks = new ArrayList<>();
//                while (matcher.find()) {
//                    String topicLine = matcher.group();
//                    if (topicLine.charAt(0) == '"') {
//                        topicLine = topicLine.substring(1, topicLine.length() - 1);
//                    }
//                    Talk parsed = p.parse(topicLine);
//                    selectedTalks.add(parsed);
//                }
//                votes.add(new FlatVotes(selectedTalks));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return votes;
    }
}
