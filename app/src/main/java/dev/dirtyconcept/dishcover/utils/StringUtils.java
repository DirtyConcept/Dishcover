package dev.dirtyconcept.dishcover.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

public class StringUtils {

    private StringUtils() {}

    public static String join(final @NotNull List<String> list,
                              final @NotNull String separator) {
        if (list.isEmpty()) {
            return "{}";
        }

        final StringJoiner joiner = new StringJoiner(separator, "{", "}");
        for (final String str : list) {
            joiner.add(str);
        }

        return joiner.toString();
    }
}
