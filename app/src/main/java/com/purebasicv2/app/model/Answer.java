package com.purebasicv2.app.model;

import java.util.List;

public class Answer {


    private Integer questionId;

    private List<Integer> option = null;

    public Answer() {

    }
    public Answer(Integer questionId) {
        this.questionId = questionId;
       // this.option = option;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getOption() {
        return option;
    }

    public void setOption(List<Integer> option) {
        this.option = option;
    }


}