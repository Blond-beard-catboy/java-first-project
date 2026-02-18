package org.example.world.domain;

public record VirtualWorld(
        Hero hero,
        Pet pet,
        Artifact artifact,
        EnvironmentSnapshot environment,
        DayEvent dayEvent
) {
}
