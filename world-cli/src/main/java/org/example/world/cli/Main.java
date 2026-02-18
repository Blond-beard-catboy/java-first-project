package org.example.world.cli;

import org.example.world.domain.Artifact;
import org.example.world.domain.DayEvent;
import org.example.world.domain.EnvironmentSnapshot;
import org.example.world.domain.Hero;
import org.example.world.domain.Pet;
import org.example.world.domain.VirtualWorld;
import org.example.world.domain.WorldCatalog;
import org.example.world.generator.WeatherSelection;
import org.example.world.generator.WorldGenerator;

import java.util.Scanner;

public class Main {
    private static final long RESULT_LINE_DELAY_MS = 260L;
    private static final long RESULT_SECTION_DELAY_MS = 520L;

    public static void main(String[] args) {
        WorldGenerator generator = new WorldGenerator();

        try (Scanner scanner = new Scanner(System.in)) {
            printLine("=== Генератор виртуального мира ===");
            String heroName = readNonEmptyLine(scanner, "Введите имя героя: ");
            int classChoice = readIntInRange(
                    scanner,
                    "Выберите класс героя:%n1. Воин%n2. Маг%n3. Лучник%n4. Лекарь%nВаш выбор: ",
                    1,
                    4
            );

            int[] attributes = distributeAttributes(scanner, WorldCatalog.TOTAL_ATTRIBUTE_POINTS);
            String requestedWeather = readOptionalLine(
                    scanner,
                    "Введите желаемую погоду (дождь/туман/ясно/буря) или нажмите Enter для случайной: "
            );

            WeatherSelection weatherSelection = generator.selectWeather(requestedWeather);
            if (weatherSelection.fallbackUsed()) {
                printLine("Такой погоды в каталоге нет. Выбрана случайная погода.");
            }

            VirtualWorld world = generator.createWorld(
                    heroName,
                    classChoice,
                    attributes,
                    weatherSelection.weather()
            );

            printWorld(world);
        }
    }

    private static void printWorld(VirtualWorld world) {
        Hero hero = world.hero();
        Pet pet = world.pet();
        Artifact artifact = world.artifact();
        EnvironmentSnapshot environment = world.environment();
        DayEvent dayEvent = world.dayEvent();
        int[] luckRolls = hero.luckRolls();

        printSlowSection("%n=== Виртуальный мир создан ===%n");
        printSlowSection("Объект 1. Герой%n");
        printSlow("Имя: %s%n", hero.name());
        printSlow("Класс: %s%n", hero.heroClass().title());
        printSlow("Здоровье: %d%n", hero.heroClass().health());
        printSlow("Атака: %d%n", hero.heroClass().attack());
        printSlow("Сила заклинаний: %d%n", hero.heroClass().magicPower());
        printSlow(
                "Сила: %d, Ловкость: %d, Интеллект: %d, Харизма: %d%n",
                hero.strength(),
                hero.dexterity(),
                hero.intelligence(),
                hero.charisma()
        );
        printSlow("Происхождение: %s%n", hero.origin());
        printSlow("Черта характера: %s%n", hero.trait());
        printSlow("Удача: %d (%d + %d + %d)%n", hero.luck(), luckRolls[0], luckRolls[1], luckRolls[2]);
        printSlow("Знак судьбы: %s%n", hero.fate());

        printSlowSection("%nОбъект 2. Питомец%n");
        printSlow("Тип: %s%n", pet.type());
        printSlow("Окрас: %s%n", pet.color());
        printSlow("Особое умение: %s%n", pet.skill());
        printSlow("Любимое лакомство: %s%n", pet.snack());
        printSlow("Привязанность: %d/20%n", pet.attachment());

        printSlowSection("%nОбъект 3. Таинственный предмет%n");
        printSlow("Название: %s%n", artifact.name());
        printSlow("Возраст: %s%n", artifact.age());
        printSlow("Материал: %s%n", artifact.material());
        printSlow("Свойство: %s%n", artifact.property());
        printSlow("Заряд мощности: %d/100%n", artifact.powerCharge());

        printSlowSection("%nОбъект 4. Окружение%n");
        printSlow("Локация: %s%n", environment.location());
        printSlow("Погода: %s%n", environment.weather());
        printSlow("Атмосфера: %s%n", environment.atmosphere());

        printSlowSection("%nОбъект 5. Событие дня%n");
        printSlow("Неожиданная встреча: %s%n", dayEvent.meeting());
        printSlow("Итог: герой %s.%n", dayEvent.outcome());
        printSlow(
                "Уровень необычности: %d/24, риск: %s%n",
                dayEvent.oddnessLevel(),
                dayEvent.dangerLevel()
        );

        printSlowSection("%n--- Сценарий мира --- %n");
        printSlow(
                "%s (%s) прибывает в %s, где погода: %s, а атмосфера %s. %n" +
                        "Рядом с героем шагает %s (%s), который %s. %n" +
                        "В этот день появляется %s, и герой %s. %n" +
                        "Ключом к развязке становится артефакт \"%s\" из материала \"%s\": он %s. %n" +
                        "Текущий заряд %d/100, а значит %s.%n",
                hero.name(),
                hero.heroClass().title(),
                environment.location(),
                environment.weather(),
                environment.atmosphere(),
                pet.type(),
                pet.color(),
                pet.skill(),
                dayEvent.meeting(),
                dayEvent.outcome(),
                artifact.name(),
                artifact.material(),
                artifact.property(),
                artifact.powerCharge(),
                hero.fate()
        );
    }

    private static int[] distributeAttributes(Scanner scanner, int totalPoints) {
        String[] attributes = {"Сила", "Ловкость", "Интеллект", "Харизма"};
        int[] values = new int[attributes.length];
        int remainingPoints = totalPoints;

        for (int i = 0; i < attributes.length - 1; i++) {
            String prompt = String.format("%s (введите от 0 до %d): ", attributes[i], remainingPoints);
            int allocated = readIntInRange(scanner, prompt, 0, remainingPoints);
            values[i] = allocated;
            remainingPoints -= allocated;
        }

        values[attributes.length - 1] = remainingPoints;
        System.out.printf(
                "Оставшиеся %d очков добавлены в характеристику \"%s\".%n",
                remainingPoints,
                attributes[attributes.length - 1]
        );
        return values;
    }

    private static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.printf(prompt);
            String rawValue = scanner.nextLine().trim();
            int parsedValue;

            try {
                parsedValue = Integer.parseInt(rawValue);
            } catch (NumberFormatException exception) {
                printLine("Ошибка: нужно ввести целое число.");
                continue;
            }

            if (parsedValue < min || parsedValue > max) {
                System.out.printf("Ошибка: допустимый диапазон от %d до %d.%n", min, max);
            } else {
                return parsedValue;
            }
        }
    }

    private static String readNonEmptyLine(Scanner scanner, String prompt) {
        while (true) {
            System.out.printf(prompt);
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                printLine("Имя не может быть пустым. Повторите ввод.");
            } else {
                return value;
            }
        }
    }

    private static String readOptionalLine(Scanner scanner, String prompt) {
        System.out.printf(prompt);
        return scanner.nextLine().trim();
    }

    private static void printSlow(String format, Object... args) {
        System.out.printf(format, args);
        pause(RESULT_LINE_DELAY_MS);
    }

    private static void printSlowSection(String format, Object... args) {
        System.out.printf(format, args);
        pause(RESULT_SECTION_DELAY_MS);
    }

    private static void pause(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private static void printLine(String message) {
        System.out.printf("%s%n", message);
    }
}
