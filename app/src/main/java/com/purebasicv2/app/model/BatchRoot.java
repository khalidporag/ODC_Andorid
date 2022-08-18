package com.purebasicv2.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchRoot {
    public class Batch  implements Serializable {
        public int id;
        public int student_id;
        public int batch_id;
        public int fees;
        public int payable;
        public int paid;
        public Object discount;
        public String enroll_at;
        public Object subscription_start;
        public Object subscription_end;
        public int enroll_status;
        public int status;
        public String created_at;
        public String updated_at;
        public int created_by;
        public Object updated_by;
        public String title = "Dummy Class";
        public Course course;

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public String getTitle() {
            if (title == null)
                return "Dummy Class";
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStudent_id() {
            return student_id;
        }

        public void setStudent_id(int student_id) {
            this.student_id = student_id;
        }

        public int getBatch_id() {
            return batch_id;
        }

        public void setBatch_id(int batch_id) {
            this.batch_id = batch_id;
        }

        public int getFees() {
            return fees;
        }

        public void setFees(int fees) {
            this.fees = fees;
        }

        public int getPayable() {
            return payable;
        }

        public void setPayable(int payable) {
            this.payable = payable;
        }

        public int getPaid() {
            return paid;
        }

        public void setPaid(int paid) {
            this.paid = paid;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public String getEnroll_at() {
            return enroll_at;
        }

        public void setEnroll_at(String enroll_at) {
            this.enroll_at = enroll_at;
        }

        public Object getSubscription_start() {
            return subscription_start;
        }

        public void setSubscription_start(Object subscription_start) {
            this.subscription_start = subscription_start;
        }

        public Object getSubscription_end() {
            return subscription_end;
        }

        public void setSubscription_end(Object subscription_end) {
            this.subscription_end = subscription_end;
        }

        public int getEnroll_status() {
            return enroll_status;
        }

        public void setEnroll_status(int enroll_status) {
            this.enroll_status = enroll_status;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getCreated_by() {
            return created_by;
        }

        public void setCreated_by(int created_by) {
            this.created_by = created_by;
        }

        public Object getUpdated_by() {
            return updated_by;
        }

        public void setUpdated_by(Object updated_by) {
            this.updated_by = updated_by;
        }

    }

    public List<Batch> data = new ArrayList<>();

    public List<Batch> getData() {
        return data;
    }

    public void setData(List<Batch> data) {
        this.data = data;
    }
}
