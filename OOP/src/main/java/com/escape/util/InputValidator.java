package com.escape.util;

/** Basic input sanitization for the text game. */
public final class InputValidator {

    private InputValidator() {}

    public static String sanitize(String input) {
        if (input == null) return "";
        return input.trim().replaceAll("[\\p{Cntrl}]", "");
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}
