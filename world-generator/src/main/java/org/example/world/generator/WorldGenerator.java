package org.example.world.generator;

import org.example.world.domain.Artifact;
import org.example.world.domain.DayEvent;
import org.example.world.domain.EnvironmentSnapshot;
import org.example.world.domain.Hero;
import org.example.world.domain.HeroClassProfile;
import org.example.world.domain.Pet;
import org.example.world.domain.VirtualWorld;
import org.example.world.domain.WorldCatalog;

import java.util.Arrays;

public class WorldGenerator {
    public HeroClassProfile resolveHeroClass(int choice) {
        return switch (choice) {
            case 1 -> new HeroClassProfile("Воин", 95, 20, 4);
            case 2 -> new HeroClassProfile("Маг", 65, 8, 24);
            case 3 -> new HeroClassProfile("Лучник", 78, 16, 10);
            case 4 -> new HeroClassProfile("Лекарь", 72, 9, 18);
            default -> new HeroClassProfile("Странник", 70, 12, 12);
        };
    }

    public WeatherSelection selectWeather(String requestedWeather) {
        int weatherIndex = RandomSupport.findIndexIgnoreCase(WorldCatalog.WEATHER_OPTIONS, requestedWeather);
        String weather;
        boolean fallbackUsed = false;

        if (!requestedWeather.isBlank() && weatherIndex >= 0) {
            weather = WorldCatalog.WEATHER_OPTIONS[weatherIndex];
        } else {
            weather = RandomSupport.randomFrom(WorldCatalog.WEATHER_OPTIONS);
            fallbackUsed = !requestedWeather.isBlank();
        }

        return new WeatherSelection(weather, fallbackUsed);
    }

    public VirtualWorld createWorld(String heroName, int classChoice, int[] attributes, String weather) {
        Hero hero = createHero(heroName, classChoice, attributes);
        Pet pet = createPet();
        Artifact artifact = createArtifact();
        EnvironmentSnapshot environment = createEnvironment(weather);
        DayEvent dayEvent = createDayEvent();
        return new VirtualWorld(hero, pet, artifact, environment, dayEvent);
    }

    private Hero createHero(String heroName, int classChoice, int[] attributes) {
        HeroClassProfile heroClass = resolveHeroClass(classChoice);
        int[] normalizedAttributes = normalizeAttributes(attributes);
        int strength = normalizedAttributes[0];
        int dexterity = normalizedAttributes[1];
        int intelligence = normalizedAttributes[2];
        int charisma = normalizedAttributes[3];

        String origin = RandomSupport.randomFrom(WorldCatalog.ORIGIN_OPTIONS);
        String trait = RandomSupport.randomFrom(WorldCatalog.TRAIT_OPTIONS);
        int[] luckRolls = RandomSupport.rollDice(WorldCatalog.LUCK_DICE_COUNT, WorldCatalog.LUCK_DICE_SIDES);
        int luck = RandomSupport.sum(luckRolls);
        String fate = luck >= 12 ? "судьба благоволит герою" : "мир готовит герою испытание";

        return new Hero(
                heroName,
                heroClass,
                strength,
                dexterity,
                intelligence,
                charisma,
                origin,
                trait,
                luck,
                luckRolls,
                fate
        );
    }

    private Pet createPet() {
        String petType = RandomSupport.randomFrom(WorldCatalog.PET_TYPES);
        String petColor = RandomSupport.randomFrom(WorldCatalog.PET_COLORS);
        String petSkill = RandomSupport.randomFrom(WorldCatalog.PET_SKILLS);
        String petSnack = RandomSupport.randomFrom(WorldCatalog.PET_SNACKS);
        int attachment = RandomSupport.rollParameter(2, 10);
        return new Pet(petType, petColor, petSkill, petSnack, attachment);
    }

    private Artifact createArtifact() {
        String artifactName = RandomSupport.randomFrom(WorldCatalog.ARTIFACT_NAMES);
        String artifactMaterial = RandomSupport.randomFrom(WorldCatalog.ARTIFACT_MATERIALS);
        String artifactProperty = RandomSupport.randomFrom(WorldCatalog.ARTIFACT_PROPERTIES);
        String artifactAge = RandomSupport.randomFrom(WorldCatalog.ARTIFACT_AGES);
        int powerCharge = RandomSupport.rollParameter(4, 25);
        return new Artifact(artifactName, artifactAge, artifactMaterial, artifactProperty, powerCharge);
    }

    private EnvironmentSnapshot createEnvironment(String weather) {
        String location = RandomSupport.randomFrom(WorldCatalog.LOCATION_OPTIONS);
        String atmosphere = RandomSupport.randomFrom(WorldCatalog.ATMOSPHERE_OPTIONS);
        return new EnvironmentSnapshot(location, weather, atmosphere);
    }

    private DayEvent createDayEvent() {
        String meeting = RandomSupport.randomFrom(WorldCatalog.EVENT_MEETINGS);
        String outcome = RandomSupport.randomFrom(WorldCatalog.EVENT_OUTCOMES);
        int oddnessLevel = RandomSupport.rollParameter(3, 8);
        String dangerLevel = switch (oddnessLevel / 6) {
            case 0 -> "низкий";
            case 1 -> "умеренный";
            case 2 -> "высокий";
            default -> "критический";
        };
        return new DayEvent(meeting, outcome, oddnessLevel, dangerLevel);
    }

    private int[] normalizeAttributes(int[] attributes) {
        if (attributes == null || attributes.length < 4) {
            return new int[]{0, 0, 0, 0};
        }
        return Arrays.copyOf(attributes, 4);
    }
}
