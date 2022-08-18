package com.purebasicv2.app.model;

public class ModelTestItems {

    private int id;
    private String name;
    private int exam_time;

    public ModelTestItems(int id, String name, int exam_time) {
        this.id = id;
        this.name = name;
        this.exam_time = exam_time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExam_time() {
        return exam_time;
    }
}
