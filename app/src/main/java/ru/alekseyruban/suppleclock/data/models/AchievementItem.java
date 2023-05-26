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

    public Boolean getReceived() {
        return isReceived;
    }

    public void setReceived(Boolean received) {
        isReceived = received;
    }

    private Boolean isReceived;



    public AchievementItem(String imageUri, String name, String description, boolean isReceived) {
        this.imageUri = imageUri;
        this.name = name;
        this.description = description;
        this.isReceived = isReceived;
    }

}
