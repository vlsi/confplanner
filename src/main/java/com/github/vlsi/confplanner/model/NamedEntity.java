package com.github.vlsi.confplanner.model;

import org.optaplanner.core.api.domain.lookup.PlanningId;

public class NamedEntity {
    private final String name;

    public NamedEntity(String name) {
        this.name = name;
    }

    @PlanningId
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamedEntity that = (NamedEntity) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                '}';
    }
}
