package com.purebasicv2.app.model;

import java.io.Serializable;

public class CategoryItems implements Serializable {

    private String slug;
    private String name;
    private int icon;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public CategoryItems(String slug, String name, int icon) {
        this.slug = slug;
        this.name = name;
        this.icon = icon;
    }
}
