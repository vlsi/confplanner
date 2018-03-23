package com.github.vlsi.confplanner.solver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComplexityScorer {
    public static long score(List<String> complexities) {
        Map<String, Long> map = complexities.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return map
                .values().stream().mapToLong(x -> x * x).sum();
    }
}
