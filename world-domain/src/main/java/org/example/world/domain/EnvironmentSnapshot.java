package org.example.world.domain;

public record EnvironmentSnapshot(
        String location,
        String weather,
        String atmosphere
) {
}
