package com.tonilopezmr.androidtesting.got.model;

public class GoTCharacter {

    private String name;
    private String imageUrl;
    private String desc;
    private String houseName;

    public GoTCharacter(String name, String imageUrl, String description, String houseName) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.desc = description;
        this.houseName = houseName;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return desc;
    }

    public String getHouseName() {
        return houseName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoTCharacter that = (GoTCharacter) o;

        if (!name.equals(that.name)) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (!desc.equals(that.desc)) return false;
        return houseName != null ? houseName.equals(that.houseName) : that.houseName == null;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + desc.hashCode();
        result = 31 * result + (houseName != null ? houseName.hashCode() : 0);
        return result;
    }
}
