package com.github.vlsi.confplanner.model;

import java.util.regex.Pattern;

public class SpaceCleaner {
    private static Pattern space = Pattern.compile("[\\p{Zs}\\s]+");

    public String clean(String in) {
        return space.matcher(in).replaceAll(" ");
    }
}
