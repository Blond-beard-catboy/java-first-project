package org.example.world.domain;

public record DayEvent(
        String meeting,
        String outcome,
        int oddnessLevel,
        String dangerLevel
) {
}
