package org.example.world.domain;

public final class WorldCatalog {
    public static final int TOTAL_ATTRIBUTE_POINTS = 12;
    public static final int LUCK_DICE_COUNT = 3;
    public static final int LUCK_DICE_SIDES = 6;

    public static final String[] ORIGIN_OPTIONS = {
            "из дворянского рода",
            "с улиц большого города",
            "из лесной глуши",
            "с далеких парящих островов",
            "из подземных шахт кристального хребта"
    };
    public static final String[] TRAIT_OPTIONS = {
            "решительный",
            "осторожный",
            "авантюрный",
            "добродушный",
            "хитрый"
    };

    public static final String[] PET_TYPES = {
            "дракончик",
            "лиса-дух",
            "механический паук",
            "лунный кот"
    };
    public static final String[] PET_COLORS = {
            "янтарный",
            "угольно-черный",
            "серебристый",
            "изумрудный"
    };
    public static final String[] PET_SKILLS = {
            "раскрывает скрытые ловушки",
            "исцеляет хозяина короткой песней",
            "поглощает часть магического урона",
            "ускоряет передвижение отряда"
    };
    public static final String[] PET_SNACKS = {
            "кристаллический мед",
            "жареные семена тумана",
            "метеоритные ягоды",
            "энергетическое печенье"
    };

    public static final String[] ARTIFACT_NAMES = {
            "Сердце Бури",
            "Шепот Оникса",
            "Клинок Забытой Зари",
            "Куб Переплетенных Путей"
    };
    public static final String[] ARTIFACT_MATERIALS = {
            "эбонитовое дерево",
            "сияющий кристалл",
            "даэдрический металл",
            "вулканическое стекло"
    };
    public static final String[] ARTIFACT_PROPERTIES = {
            "светится в темноте",
            "тихо шепчет древние подсказки",
            "меняет форму в руках владельца",
            "пульсирует при приближении опасности"
    };
    public static final String[] ARTIFACT_AGES = {
            "300 лет",
            "850 лет",
            "1200 лет",
            "неизвестный возраст"
    };

    public static final String[] LOCATION_OPTIONS = {
            "парящий город Аэрис",
            "подледный порт Нордхейм",
            "механический каньон Феррус",
            "лунная долина Элион"
    };
    public static final String[] WEATHER_OPTIONS = {
            "дождь",
            "туман",
            "ясно",
            "буря"
    };
    public static final String[] ATMOSPHERE_OPTIONS = {
            "тихая и настороженная",
            "шумная и праздничная",
            "наэлектризованная ожиданием",
            "мрачная, но вдохновляющая"
    };

    public static final String[] EVENT_MEETINGS = {
            "старый боевой товарищ",
            "странный незнакомец в маске",
            "архивариус запретной библиотеки",
            "инженер с картой аномалий"
    };
    public static final String[] EVENT_OUTCOMES = {
            "находит редкий ресурс",
            "теряет часть припасов",
            "получает секретную подсказку",
            "вынужден вступить в короткий бой"
    };

    private WorldCatalog() {
    }
}
