package com.github.vlsi.confplanner.solver;

import java.util.Collection;
import java.util.Set;

public class LanguageDiversityScorer {
    public static int score(Collection<String> langs) {
        int en = 0;
        int ru = 0;
        for (String lang : langs) {
            if ("en".equals(lang)) {
                en++;
            }
            if ("ru".equals(lang)) {
                ru++;
            }
        }
        if (en == 0 || ru == 0) {
            return -100;
        }
        return Math.min(en, ru) * 20;
    }

    public static <E> long intersectSize(Set<E> a, Set<E> b) {
        return a.stream().filter(b::contains).count();
    }
}
