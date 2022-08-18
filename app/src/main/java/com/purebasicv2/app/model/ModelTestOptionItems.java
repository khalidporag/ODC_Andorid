package com.purebasicv2.app.model;

public class ModelTestOptionItems {
    private int optionId;
    private String optionName;
    private int correct;

    public ModelTestOptionItems(int optionId, String optionName, int correct) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.correct = correct;
    }

    public int getOptionId() {
        return optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public int getCorrect() {
        return correct;
    }
}