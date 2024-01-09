package dev.dirtyconcept.dishcover.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]{4,16}$");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^[A-Za-z\\d@$!%*?&]{6,}$");

    @Contract(pure = true)
    private ValidationUtils() {}

    @Contract(pure = true)
    public static boolean isEmpty(final @NotNull String... values) {
        for (String value : values) {
            if (value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidUsername(final @NotNull CharSequence sequence) {
        return USERNAME_REGEX.matcher(sequence).matches();
    }

    public static boolean isValidEmail(final @NotNull CharSequence sequence) {
        return EMAIL_REGEX.matcher(sequence).matches();
    }

    public static boolean isValidPassword(final @NotNull CharSequence sequence) {
        return PASSWORD_REGEX.matcher(sequence).matches();
    }
}
