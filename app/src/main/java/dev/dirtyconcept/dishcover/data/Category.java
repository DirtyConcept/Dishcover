package dev.dirtyconcept.dishcover.data;

import java.util.Optional;

public enum Category {
    PIZZA(0),
    MEAT(1),
    SPAGHETTI(2),
    BURGERS(3),
    TOAST(4),
    SALAD(5),
    SOUP(6)
    // More to come...
    ;


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

    public static Category fromId(int id) {
        return findCategoryById(id).orElse(null);
    }
}