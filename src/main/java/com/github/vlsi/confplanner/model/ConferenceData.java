package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.vlsi.confplanner.solver.ExpectedNumListeners;
import com.github.vlsi.confplanner.solver.ScoringParams;
import com.github.vlsi.confplanner.solver.TalkConflict;
import com.github.vlsi.confplanner.solver.TalkPlacement;
import com.github.vlsi.confplanner.votes.FlatVotes;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.Indictment;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonPropertyOrder({"capacity", "languages", "rooms", "days", "timeslots", "roomAvailability", "roomUnavailability", "topics", "speakers", "talks", "talkSequence", "talkPlacements"})
@PlanningSolution
public class ConferenceData {
    private int capacity;
    private List<Speaker> speakers;
    private List<Room> rooms;
    private List<Language> languages;
    private List<Topic> topics;
    private List<Talk> talks;
    private List<Timeslot> timeslots;
    private List<Day> days;
    private List<RoomTimeslots> roomAvailability;
    private List<RoomTimeslots> roomUnavailability;
    //    @JsonIgnore
    private List<TalkPlacement> talkPlacements;
    private List<ExpectedNumListeners> expectedNumListeners;
    private Collection<ExpectedNumListeners> computedListeners;
    private List<TalkSequence> talkSequence;

    private List<FlatVotes> votes;

    //    @JsonIgnore
    private List<TalkPlace> talkPositions;

    @JsonIgnore
    private Map<Talk, Talk> talkMap;
    @JsonIgnore
    private Map<Talk, TalkPlacement> talkPlacementMap;

    // planning
    private HardSoftScore score;
    @JsonIgnore
    private Map<Talk, ExpectedNumListeners> allExpectedListeners;
    @JsonIgnore
    private Collection<TalkConflict> talkConflicts;
    private int roomCapacityFactor;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    @ValueRangeProvider(id = "roomsProvider")
    @ProblemFactCollectionProperty
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @ProblemFactCollectionProperty
    public List<Talk> getTalks() {
        return talks;
    }

    @JsonProperty
    public void setTalks(List<Talk> talks) {
        this.talks = talks;
        this.talkPlacements = talks.stream().map(TalkPlacement::new).collect(Collectors.toList());
        this.talkPlacementMap = talkPlacements.stream().collect(Collectors.toMap(TalkPlacement::getTalk, Function.identity()));
        this.talkMap = talks.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @ValueRangeProvider(id = "timeslotsProvider")
    @ProblemFactCollectionProperty
    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public List<RoomTimeslots> getRoomAvailability() {
        return roomAvailability;
    }

    public void setRoomAvailability(List<RoomTimeslots> roomAvailability) {
        this.roomAvailability = roomAvailability;
    }

    public List<RoomTimeslots> getRoomUnavailability() {
        return roomUnavailability;
    }

    public void setRoomUnavailability(List<RoomTimeslots> roomUnavailability) {
        this.roomUnavailability = roomUnavailability;
    }

    public List<ExpectedNumListeners> getExpectedNumListeners() {
        return expectedNumListeners;
    }

    public void setExpectedNumListeners(List<ExpectedNumListeners> expectedNumListeners) {
        this.expectedNumListeners = expectedNumListeners;
    }

    public List<TalkPlace> getTalkPositions() {
        return talkPositions;
    }

    public void setTalkPositions(List<TalkPlace> talkPositions) {
        this.talkPositions = talkPositions;
        for (TalkPlace p : talkPositions) {
            TalkPlacement place = talkPlacementMap.get(p.getTalk());
            place.setSlot(p.getSlot());
            place.setRoom(p.getRoom());
            place.setMoveable(false);
        }
    }

    // Planning
    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    @ProblemFactCollectionProperty
    public List<TalkSequence> getTalkSequence() {
        return talkSequence == null ? Collections.emptyList() : talkSequence;
    }

    public void setTalkSequence(List<TalkSequence> talkSequence) {
        this.talkSequence = talkSequence;
        for (TalkSequence sequence : talkSequence) {
            talkPlacementMap.get(sequence.getFirst()).setHasSequence(true);
            talkPlacementMap.get(sequence.getSecond()).setHasSequence(true);
        }
    }

    @ProblemFactCollectionProperty
    public Collection<ExpectedNumListeners> getAllExpectedListeners() {
        return allExpectedListeners.values();
    }

    @PlanningEntityCollectionProperty
    @JsonIgnore
    public List<TalkPlacement> getTalkPlacements() {
        return talkPlacements;
    }

    @ValueRangeProvider(id = "roomTimeslots")
    @ProblemFactCollectionProperty
    @JsonIgnore
    public List<RoomTimeslot> getAllRooms() {
        Set<RoomTimeslot> res = new LinkedHashSet<>();
        Map<Room, List<RoomTimeslot>> customAvailable = groupSlots(getRoomAvailability());
        Map<Room, List<RoomTimeslot>> customUnavailable = groupSlots(getRoomUnavailability());
        for (Room room : getRooms()) {
            List<RoomTimeslot> timeslots = customAvailable.get(room);
            if (timeslots != null) {
                res.addAll(timeslots);
                continue;
            }
            for (Timeslot timeslot : getTimeslots()) {
                res.add(new RoomTimeslot(room, timeslot));
            }
            timeslots = customUnavailable.get(room);
            if (timeslots != null) {
                res.removeAll(timeslots);
            }
        }
        return new ArrayList<>(res);
    }

    private Map<Room, List<RoomTimeslot>> groupSlots(List<RoomTimeslots> available) {
        if (available == null) {
            return Collections.emptyMap();
        }
        return available
                .stream()
                .flatMap(rts -> rts.getTimeslots().stream().map(t -> new RoomTimeslot(rts.getRoom(), t)))
                .collect(Collectors.groupingBy(RoomTimeslot::getRoom));
    }

    public List<FlatVotes> getVotes() {
        return votes;
    }

    public void setVotes(List<FlatVotes> votes) {
        this.votes = votes;

        Map<Talk, ExpectedNumListeners> expectedListeners = new HashMap<>();

        int timeslotsCount = timeslots.size();

        int totalVotes = votes.stream().mapToInt(FlatVotes::getCount).sum();

        Map<TalkConflict, TalkConflict> conflicts = new HashMap<>();
        for (FlatVotes flat : votes) {
            Collection<Talk> talks = flat.getTalks();
            if (talkMap != null) {
                List<Talk> list = new ArrayList<>();
                for (Talk talk : talks) {
                    Talk talk1 = talkMap.get(talk);
                    if (talk1 != null) {
                        list.add(talk1);
                    }
                }
                talks = list;
            }
//            List<Talk> list = new ArrayList<>();
//            for (Talk talk : talks) {
//                Talk talk1 = talkMap.get(talk);
//                list.add(talk1);
//            }
//            talks = list;
            double kConflicts = 1.0 * flat.getCount() * getCapacity() / totalVotes;// / (talks.size() * (talks.size() - 1) / 2);
            double kListeners = 1.0 * flat.getCount() * getCapacity() * timeslotsCount / totalVotes / talks.size();
            for (Talk a : talks) {
                expectedListeners.computeIfAbsent(a, ExpectedNumListeners::create).inc(kListeners);
                if (talks.size() == 1) {
                    continue;
                }
                for (Talk b : talks) {
                    if (a.getName().compareTo(b.getName()) >= 0) {
                        continue;
                    }
                    TalkConflict key = new TalkConflict(a, b);
                    conflicts.computeIfAbsent(key, x -> key).inc(kConflicts);
                }
            }
        }
        talkConflicts = conflicts.values();
        computedListeners = expectedListeners.values();
        allExpectedListeners = computeAllExpectedListeners();
        if (talkMap == null) {
            return;
        }
        for (ExpectedNumListeners e : allExpectedListeners.values()) {
            Talk talk = talkMap.get(e.getTalk());
            if (talk == null) {
                continue;
            }
            talk.setNumListeners((int) e.getCount());
        }
    }

    private Map<Talk, ExpectedNumListeners> computeAllExpectedListeners() {
        Map<Talk, ExpectedNumListeners> res = new HashMap<>();
        if (this.expectedNumListeners != null) {
            for (ExpectedNumListeners e : expectedNumListeners) {
                res.put(e.getTalk(), e);
            }
        }

        if (computedListeners != null) {
            for (ExpectedNumListeners computedListener : computedListeners) {
                Talk talk = computedListener.getTalk();
                if (!res.containsKey(talk)) {
                    res.put(talk, computedListener);
                }
            }
        }

        return res;
    }

    @ProblemFactCollectionProperty
    public Collection<TalkConflict> getTalkConflicts() {
        return talkConflicts;
    }

    public void sort() {
        speakers.sort(Comparator.comparing(Speaker::getName));
        if (topics != null) {
            topics.sort(Comparator.comparing(Topic::getName));
        }
        if (languages != null) {
            languages.sort(Comparator.comparing(Language::getName));
        }
        if (votes != null) {
            votes.sort(Comparator.naturalOrder());
        }
        if (timeslots != null) {
            timeslots.sort(Comparator.comparing(Timeslot::getStartTimestamp));
        }
        if (rooms != null) {
            rooms.sort(Comparator.comparing(Room::getName));
        }
        if (talkPlacements != null) {
            talkPlacements.sort(Comparator.comparing(TalkPlacement::getSlot, Comparator.nullsFirst(Comparator.naturalOrder()))
                    .thenComparing(TalkPlacement::getRoom, Comparator.nullsFirst(Comparator.naturalOrder())));
        }
    }

    public void print(Writer w, Map<Object, Indictment> map) {
        PrintWriter pw = new PrintWriter(w);
        Timeslot prevSlot = null;
        Map<Timeslot, Integer> expectedCount = new HashMap<>();
        for (TalkPlacement p : talkPlacements) {
            expectedCount.compute(p.getSlot(), (slot, val) -> (val == null ? 0 : val) + p.getExpectedNumListeners());
        }

        Map<Timeslot, List<TalkPlacement>> slotTalks = new LinkedHashMap<>();
        for (TalkPlacement talk : talkPlacements) {
            Timeslot slot = talk.getSlot();
            slotTalks.computeIfAbsent(slot, k -> new ArrayList<>()).add(talk);
        }

        for (Map.Entry<Timeslot, List<TalkPlacement>> entry : slotTalks.entrySet()) {
            List<TalkPlacement> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                TalkPlacement talk = value.get(i);
                if (i > 0) {
                    pw.print('\t');
                }
                pw.print('"' + talk.getTalk().getSpeakers()
                        .stream()
                        .map(Speaker::getName)
                        .collect(Collectors.joining(", ")) + '"');
            }
            pw.println("<br>");
        }
        pw.println("<br>");
        for (Map.Entry<Timeslot, List<TalkPlacement>> entry : slotTalks.entrySet()) {
            List<TalkPlacement> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                TalkPlacement talk = value.get(i);
                if (i > 0) {
                    pw.print('\t');
                }
                pw.print('"' + talk.getTalk().getFullTitle() + '"');
            }
            pw.println("<br>");
        }
        pw.println("<br>");
        for (Map.Entry<Timeslot, List<TalkPlacement>> entry : slotTalks.entrySet()) {
            List<TalkPlacement> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                TalkPlacement talk = value.get(i);
                if (i > 0) {
                    pw.print('\t');
                }
                pw.print('"' + talk.getTalk().getLanguage().getName() + '"');
            }
            pw.println("<br>");
        }
        pw.println("<br>");

        for (TalkPlacement talk : talkPlacements) {
            Timeslot slot = talk.getSlot();
            if (prevSlot != null && slot.minus(prevSlot).toHours() > 8) {
                pw.println("<br>");
            }
            if (prevSlot != slot) {
                pw.println("<br>");
                pw.println("<h3> " + slot + ", " + expectedCount.get(slot) + " listeners expected</h3><br>");

//                if (map != null) {
//                    Indictment indictment = map.get(slot);
//                }
                prevSlot = slot;
            }

//            slotTalks.add(talk);
            Room room = talk.getRoom();
            try {
                pw.println("&nbsp;&nbsp;&nbsp;&nbsp;" + room.getName() + ":" + room.getCapacity()
                        + "(" + Math.round(talk.getExpectedNumListeners()) + ")"
                        + ", " + talk.getTalk().getLanguage().getName()
                        + ", " + (talk.getTalk().getTopics() == null ? "" : talk.getTalk().getTopics().stream().map(Topic::getName).collect(Collectors.toList()))
                        + ", " + talk.getTalk().getFullTitle()
                        + "<br>");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (map == null) {
                continue;
            }
            Indictment indictment = map.get(talk);
            pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + indictment.getScoreTotal() + "<br>");
            for (ConstraintMatch match : indictment.getConstraintMatchSet()) {
                String cname = match.getConstraintName();
//                if ("visitMostTalks".equals(cname)) {
//                    pw.println("  " + );
//                    continue;
//                }
                pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + cname + " " + match.getScore() + "<br>");
                if ("visitMostTalks".equals(cname)) {
                    pw.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    Object o = match.getJustificationList().get(2);
                    String color = null;
                    if (o instanceof TalkConflict) {
                        TalkConflict tc = (TalkConflict) o;
                        if (tc.getCount() > talk.getExpectedNumListeners() / 2) {
                            color = "red";
                        } else if (tc.getCount() < talk.getExpectedNumListeners() / 4) {
                            color = "darkgreen";
                        }
                    }
                    if (color != null) {
                        pw.append("<span style='color:").append(color).append("'>");
                    }
                    pw.print(o);
                    if (color != null) {
                        pw.append("</span>");
                    }
                    pw.println("<br>");
                }
            }
        }
    }

    public void setRoomCapacityFactor(int roomCapacityFactor) {
        this.roomCapacityFactor = roomCapacityFactor;
    }

    @ProblemFactProperty
    @JsonIgnore
    public ScoringParams getScoringParams() {
        return new ScoringParams(roomCapacityFactor);
    }
}
