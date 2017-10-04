package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(scope = Topic.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Topic extends NamedEntity {
    public Topic(@JsonProperty("name") String name) {
        super(name);
    }

}
