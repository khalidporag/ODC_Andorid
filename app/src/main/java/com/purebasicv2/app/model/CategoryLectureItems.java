package com.purebasicv2.app.model;

public class CategoryLectureItems {

    private int id;
    private String name;
    private String date;
    private String  type;

    public CategoryLectureItems(int id, String name, String date, String type) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
