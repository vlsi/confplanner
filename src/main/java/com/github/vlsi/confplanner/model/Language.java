package com.github.vlsi.confplanner.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(scope = Language.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Language extends NamedEntity {
    public Language(@JsonProperty("name") String name) {
        super(name);
    }
}
