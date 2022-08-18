package com.purebasicv2.app.model;

public class ModelTestResultOptionItems {
    private String optionName;
    private int correct;
    private boolean answered;

    public String getOptionName() {
        return optionName;
    }

    public int getCorrect() {
        return correct;
    }

    public boolean isAnswered() {
        return answered;
    }

    public ModelTestResultOptionItems(String optionName, int correct, boolean answered) {
        this.optionName = optionName;
        this.correct = correct;
        this.answered = answered;
    }
}