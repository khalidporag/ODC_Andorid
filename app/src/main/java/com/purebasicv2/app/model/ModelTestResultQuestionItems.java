package com.purebasicv2.app.model;


import java.util.List;

public class ModelTestResultQuestionItems {

    private int id;
    private String questionTitle;
    private int is_multi;
    private String details;
    private List<ModelTestResultOptionItems> options;

    public int getId() {
        return id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public int getIs_multi() {
        return is_multi;
    }

    public List<ModelTestResultOptionItems> getOptions() {
        return options;
    }

    public String getDetails() {
        return details;
    }

    public ModelTestResultQuestionItems(int id, String questionTitle, int is_multi, String details, List<ModelTestResultOptionItems> options) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.is_multi = is_multi;
        this.details = details;
        this.options = options;
    }
}