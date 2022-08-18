package com.purebasicv2.app.model;

import java.util.List;

public class QuestionList {

        private Integer id;
        private Integer modeltest_id;
        private String question;
        private String detailss;
        private Integer is_multi;
        private Integer status;
        private Object created_at;
        private Object updated_at;
        private List<Option> options = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getModeltestId() {
            return modeltest_id;
        }

        public void setModeltestId(Integer modeltestId) {
            this.modeltest_id = modeltestId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getDetailss() {
            return detailss;
        }

        public void setDetailss(String detailss) {
            this.detailss = detailss;
        }

        public Integer getIsMulti() {
            return is_multi;
        }

        public void setIsMulti(Integer isMulti) {
            this.is_multi = isMulti;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(Object createdAt) {
            this.created_at = createdAt;
        }

        public Object getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updated_at = updatedAt;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }


    public class Option {

        private Integer id;
        private Integer question_id;
        private Integer modeltest_id;
        private String option;
        private Integer correct_or_not;
        private Integer status;
        private Object created_at;
        private Object updated_at;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getQuestionId() {
            return question_id;
        }

        public void setQuestionId(Integer questionId) {
            this.question_id = questionId;
        }

        public Integer getModeltestId() {
            return modeltest_id;
        }

        public void setModeltestId(Integer modeltestId) {
            this.modeltest_id = modeltestId;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public Integer getCorrectOrNot() {
            return correct_or_not;
        }

        public void setCorrectOrNot(Integer correctOrNot) {
            this.correct_or_not = correctOrNot;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(Object createdAt) {
            this.created_at = createdAt;
        }

        public Object getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updated_at = updatedAt;
        }


    }

}
