package util;

public class Util {
    public static boolean isNumeric(String str) {
        // match a number with optional '-' and decimal.
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
