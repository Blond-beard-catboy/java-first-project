package org.example;

import java.util.Scanner;

public class Main {

    // 1. Константы и массивы (String[]) для хранения наборов данных
    static final String[] ORIGIN_OPTIONS = {"из дворянского рода", "с улиц большого города", "из лесной глуши", "с далеких парящих островов"};
    static final String[] TRAIT_OPTIONS = {"решительный", "осторожный", "авантюрный", "добродушный", "хитрый"};
    static final String[] PET_TYPES = {"дракончик", "лиса-дух", "механический паук", "лунный кот"};
    static final String[] WEATHER_OPTIONS = {"дождь", "туман", "ясно", "буря"};
    static final String[] LOCATION_OPTIONS = {"парящий город Аэрис", "подледный порт Нордхейм", "механический каньон Феррус"};
    static final String[] EVENT_MEETINGS = {"старый боевой товарищ", "странный незнакомец в маске", "архивариус"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Генератор виртуального мира ===");

        // 2. Ввод данных пользователя
        System.out.print("Введите имя героя: ");
        String heroName = scanner.nextLine();

        System.out.println("Выберите класс героя:\n1. Воин\n2. Маг\n3. Лучник\n4. Лекарь");
        int classChoice = readIntInRange(scanner, "Ваш выбор: ", 1, 4);

        // 7. Условный оператор switch-expressions в стрелочной нотации
        String heroClass = switch (classChoice) {
            case 1 -> "Воин";
            case 2 -> "Маг";
            case 3 -> "Лучник";
            case 4 -> "Лекарь";
            default -> "Странник";
        };

        System.out.println("\nРаспределите 12 очков между характеристиками.");
        int strength = readIntInRange(scanner, "Сила (от 0 до 12): ", 0, 12);
        int dexterity = readIntInRange(scanner, "Ловкость (от 0 до " + (12 - strength) + "): ", 0, 12 - strength);
        int intelligence = 12 - strength - dexterity; // Остаток отдаем интеллекту
        System.out.println("Оставшиеся " + intelligence + " очков добавлены в Интеллект.");

        System.out.print("\nВведите желаемую погоду (дождь/туман/ясно/буря) или нажмите Enter для случайной: ");
        String requestedWeather = scanner.nextLine().trim();

        // 9. Поиск по массиву (проверка, есть ли введенная погода в списке)
        String weather;
        int weatherIndex = findIndexIgnoreCase(WEATHER_OPTIONS, requestedWeather);
        if (weatherIndex != -1) { // 5. Условный оператор if-else
            weather = WEATHER_OPTIONS[weatherIndex];
        } else {
            System.out.println("Погода не найдена или не введена. Выбрана случайная.");
            weather = getRandomElement(WEATHER_OPTIONS);
        }

        // 10. Math.random() для выбора случайных элементов
        String origin = getRandomElement(ORIGIN_OPTIONS);
        String trait = getRandomElement(TRAIT_OPTIONS);

        // 11. Цикл для расчёта параметра "Удача" (имитация 3 кубиков)
        int luck = 0;
        int[] diceRolls = new int[3];
        for (int i = 0; i < 3; i++) {
            diceRolls[i] = 1 + (int) (Math.random() * 6);
            luck += diceRolls[i];
        }

        // 6. Тернарный оператор
        String fate = (luck >= 12) ? "Судьба благоволит герою" : "Мир готовит герою испытание";

        // Генерация остальных объектов (Питомец, Окружение, Событие)
        String petType = getRandomElement(PET_TYPES);
        int petAttachment = 1 + (int) (Math.random() * 20); // от 1 до 20

        String location = getRandomElement(LOCATION_OPTIONS);
        String meeting = getRandomElement(EVENT_MEETINGS);

        // 4. Форматированный вывод данных в консоль
        System.out.println("\n=== ВИРТУАЛЬНЫЙ МИР СОЗДАН ===");
        System.out.printf("Объект 1. Герой: %s (%s)%n", heroName, heroClass);
        System.out.printf("Характеристики -> Сила: %d, Ловкость: %d, Интеллект: %d%n", strength, dexterity, intelligence);
        System.out.printf("Происхождение: %s | Характер: %s%n", origin, trait);
        System.out.printf("Удача: %d (%d + %d + %d)%n", luck, diceRolls[0], diceRolls[1], diceRolls[2]);
        System.out.printf("Знак судьбы: %s%n", fate);

        System.out.printf("\nОбъект 2. Питомец: %s (Привязанность: %d/20)%n", petType, petAttachment);
        System.out.printf("Объект 3. Локация: %s (Погода: %s)%n", location, weather);
        System.out.printf("Объект 4. Случайная встреча: %s%n", meeting);

        System.out.println("\n--- СЦЕНАРИЙ МИРА ---");
        System.out.printf("%s, по классу %s, родом %s, прибывает в %s.\n", heroName, heroClass, origin, location);
        System.out.printf("Погода сегодня: %s. Рядом бежит верный %s.\n", weather, petType);
        System.out.printf("Внезапно на пути появляется %s... %s!\n", meeting, fate);

        scanner.close();
    }

    // --- 13. Не менее 3-ех созданных методов ---

    // Метод 1: Безопасный ввод числа в заданном диапазоне
    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // очистка буфера
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Ошибка: введите число от %d до %d.\n", min, max);
                }
            } else {
                System.out.println("Ошибка: нужно ввести целое число.");
                scanner.nextLine(); // очистка некорректного ввода
            }
        }
    }

    // Метод 2: Выбор случайного элемента из массива
    public static String getRandomElement(String[] array) {
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    // Метод 3: Поиск элемента в массиве без учета регистра
    public static int findIndexIgnoreCase(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
