package dev.dirtyconcept.dishcover.data;

import android.graphics.Path;

import java.util.Optional;

public enum Category {
    PIZZA, MEAT, SPAGHETTI, BURGERS, TOAST, SALAD, SOUP;

    private final int id;
    Category(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<Category> findCategoryById(final int id) {
        for (Category category : values()) {
            if (category.id == id) return Optional.of(category);
        }

        return Optional.empty();
    }
}
