package org.example.world.generator;

public final class RandomSupport {
    private RandomSupport() {
    }

    public static String randomFrom(String[] values) {
        int index = (int) (Math.random() * values.length);
        return values[index];
    }

    public static int[] rollDice(int count, int sides) {
        int[] results = new int[count];
        for (int i = 0; i < count; i++) {
            results[i] = 1 + (int) (Math.random() * sides);
        }
        return results;
    }

    public static int rollParameter(int count, int sides) {
        int result = 0;
        for (int i = 0; i < count; i++) {
            result += 1 + (int) (Math.random() * sides);
        }
        return result;
    }

    public static int sum(int[] numbers) {
        int result = 0;
        for (int number : numbers) {
            result += number;
        }
        return result;
    }

    public static int findIndexIgnoreCase(String[] values, String target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
