package com.tonilopezmr.androidtesting.got.model;

public class GoTCharacter {

    private String name;
    private String imageUrl;
    private String description;
    private String houseName;

    public GoTCharacter(String name, String imageUrl, String description, String houseName) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.houseName = houseName;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getHouseName() {
        return houseName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoTCharacter character = (GoTCharacter) o;

        return name != null ? name.equals(character.name) : character.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
