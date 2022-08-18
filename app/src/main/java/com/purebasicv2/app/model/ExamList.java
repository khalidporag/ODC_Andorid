package com.purebasicv2.app.model;

import java.util.List;

public class ExamList {

        private Integer id;
        private String exam_type;
        private String name;
        private String subject;
        private Integer status;
        private Integer exam_in_minutes;
        private String ex_time;
        private String solve_shet;
        private String created_at;
        private String updated_at;
        private String batch_name;
        private Boolean already_taken;
        private List<Batch> batch = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getExamType() {
            return exam_type;
        }

        public void setExamType(String examType) {
            this.exam_type = examType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getExamInMinutes() {
            return exam_in_minutes;
        }

        public void setExamInMinutes(Integer examInMinutes) {
            this.exam_in_minutes = examInMinutes;
        }

        public String getExTime() {
            return ex_time;
        }

        public void setExTime(String exTime) {
            this.ex_time = ex_time;
        }

        public String getSolveShet() {
            return solve_shet;
        }

        public void setSolveShet(String solveShet) {
            this.solve_shet = solveShet;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String createdAt) {
            this.created_at = createdAt;
        }

        public String getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updated_at = updatedAt;
        }

        public String getBatchName() {
            return batch_name;
        }

        public void setBatchName(String batchName) {
            this.batch_name = batchName;
        }

        public Boolean getAlreadyTaken() {
            return already_taken;
        }

        public void setAlreadyTaken(Boolean alreadyTaken) {
            this.already_taken = alreadyTaken;
        }

        public List<Batch> getBatch() {
            return batch;
        }

        public void setBatch(List<Batch> batch) {
            this.batch = batch;
        }





    public class Batch {

        private Integer id;
        private String plan;
        private String type;
        private String graduation;
        private String duration;
        private Integer subscription_days;
        private String ammount;
        private String yr;
        private String batch_id;
        private Integer show;
        private Integer status;
        private String created_at;
        private Object updated_at;
        private Pivot pivot;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGraduation() {
            return graduation;
        }

        public void setGraduation(String graduation) {
            this.graduation = graduation;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public Integer getSubscriptionDays() {
            return subscription_days;
        }

        public void setSubscriptionDays(Integer subscriptionDays) {
            this.subscription_days = subscriptionDays;
        }

        public String getAmmount() {
            return ammount;
        }

        public void setAmmount(String ammount) {
            this.ammount = ammount;
        }

        public String getYr() {
            return yr;
        }

        public void setYr(String yr) {
            this.yr = yr;
        }

        public String getBatchId() {
            return batch_id;
        }

        public void setBatchId(String batchId) {
            this.batch_id = batchId;
        }

        public Integer getShow() {
            return show;
        }

        public void setShow(Integer show) {
            this.show = show;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String createdAt) {
            this.created_at = createdAt;
        }

        public Object getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updated_at = updatedAt;
        }

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }

    }




    public class Pivot {

        private Integer modeltest_id;
        private Integer membershipe_id;

        public Integer getModeltestId() {
            return modeltest_id;
        }

        public void setModeltestId(Integer modeltestId) {
            this.modeltest_id = modeltestId;
        }

        public Integer getMembershipeId() {
            return membershipe_id;
        }

        public void setMembershipeId(Integer membershipeId) {
            this.membershipe_id = membershipeId;
        }

    }
}
