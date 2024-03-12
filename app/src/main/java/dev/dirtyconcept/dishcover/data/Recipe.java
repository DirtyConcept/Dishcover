package dev.dirtyconcept.dishcover.data;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import dev.dirtyconcept.dishcover.utils.StringUtils;

public class Recipe {
    private String name;
    private List<String> steps;
    private String uploader;
    private List<String> links;
    private String description;
    private List<String> tags;
    private String category;

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

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static Recipe fromFirestoreDocument(QueryDocumentSnapshot document) {
        return document.toObject(Recipe.class);
    }

    @NonNull
    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", steps=" + StringUtils.join(steps, ", ") +
                ", uploader='" + uploader + '\'' +
                ", links=" + StringUtils.join(links, ", ") +
                ", description='" + description + '\'' +
                ", tags=" + StringUtils.join(tags, ", ") +
                ", category=" + category +
                '}';
    }
}
