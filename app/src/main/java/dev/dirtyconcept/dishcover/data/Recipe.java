package dev.dirtyconcept.dishcover.data;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;

public class Recipe {
    private String name;
    private String[] steps;
    private String uploader;
    private String[] links;
    private String description;
    private Tag[] tags;
    private Category category;

    // Default constructor required for Firestore
    public Recipe() {
    }

    // Getters and setters for the fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static Recipe fromFirestoreDocument(QueryDocumentSnapshot document) {
        Recipe recipe = document.toObject(Recipe.class);

        // Convert tag and category IDs to enums
        recipe.setTags(Arrays.stream(recipe.getTagIds())
                .mapToObj(Tag::fromId)
                .toArray(Tag[]::new));

        recipe.setCategory(Category.fromId(recipe.getCategoryId()));

        return recipe;
    }

    // Other methods...

    private int[] getTagIds() {
        return Arrays.stream(tags)
                .map(Tag::getId)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private int getCategoryId() {
        return category != null ? category.getId() : -1;
    }

    @NonNull
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", steps=" + Arrays.toString(steps) +
                ", uploader='" + uploader + '\'' +
                ", links=" + Arrays.toString(links) +
                ", description='" + description + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", category=" + category +
                '}';
    }
}
