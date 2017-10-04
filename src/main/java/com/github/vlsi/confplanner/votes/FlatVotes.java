package com.github.vlsi.confplanner.votes;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.vlsi.confplanner.model.Talk;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"count", "talks"})
public class FlatVotes implements Comparable<FlatVotes> {
    private static final Comparator<FlatVotes> CMP = Comparator.comparing(FlatVotes::getCount, Comparator.reverseOrder())
            .thenComparing(Comparator.comparing(fv -> fv.getTalks().size()))
            .thenComparing(Comparator.comparing(fv -> fv.getTalks().stream().map(Talk::getName).collect(Collectors.joining(","))));

    @JsonIdentityReference(alwaysAsId = true)
    private final List<Talk> talks;
    private int count;

    public FlatVotes(@JsonProperty("talks") List<Talk> talks, @JsonProperty("count") int count) {
        this.talks = talks;
        this.count = count;
        talks.sort(Comparator.comparing(Talk::getName));
    }

    public FlatVotes(List<Talk> talks) {
        this(talks, 1);
    }

    public List<Talk> getTalks() {
        return talks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlatVotes flatVotes = (FlatVotes) o;

        return talks != null ? talks.equals(flatVotes.talks) : flatVotes.talks == null;
    }

    @Override
    public int hashCode() {
        return talks != null ? talks.hashCode() : 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(FlatVotes o) {
        return CMP.compare(this, o);
    }
}
