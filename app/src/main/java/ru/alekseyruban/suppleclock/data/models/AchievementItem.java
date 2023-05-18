package ru.alekseyruban.suppleclock.data.models;

public class AchievementItem {

    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String description;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public AchievementItem(String imageUri, String name, String description) {
        this.imageUri = imageUri;
        this.name = name;
        this.description = description;
    }

}
