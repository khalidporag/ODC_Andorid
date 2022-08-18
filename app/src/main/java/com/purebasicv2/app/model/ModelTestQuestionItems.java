package com.purebasicv2.app.model;


import java.util.List;

public class ModelTestQuestionItems {

    private int id;
    private String questionTitle;
    private int is_multi;
    private List<ModelTestOptionItems> options;

    public int getId() {
        return id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public int getIs_multi() {
        return is_multi;
    }

    public List<ModelTestOptionItems> getOptions() {
        return options;
    }

    public ModelTestQuestionItems(int id, String questionTitle, int is_multi, List<ModelTestOptionItems> options) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.is_multi = is_multi;
        this.options = options;
    }
}