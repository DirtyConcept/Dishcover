package dev.dirtyconcept.dishcover.data;

import java.util.Optional;

public enum Tag {
    HIGH_PROTEIN(0),
    LOW_CARBS(1),
    GLUTEN_FREE(2)
    // More to come...
    ;

    private final int id;

    Tag(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<Tag> findTagById(final int id) {
        for (Tag tag : values()) {
            if (tag.getId() == id) return Optional.of(tag);
        }

        return Optional.empty();
    }

    public static Tag fromId(int id) {
        return findTagById(id).orElse(null);
    }
}
