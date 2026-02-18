package org.example.world.domain;

import java.util.Arrays;

public record Hero(
        String name,
        HeroClassProfile heroClass,
        int strength,
        int dexterity,
        int intelligence,
        int charisma,
        String origin,
        String trait,
        int luck,
        int[] luckRolls,
        String fate
) {
    public Hero {
        luckRolls = Arrays.copyOf(luckRolls, luckRolls.length);
    }

    @Override
    public int[] luckRolls() {
        return Arrays.copyOf(luckRolls, luckRolls.length);
    }
}
