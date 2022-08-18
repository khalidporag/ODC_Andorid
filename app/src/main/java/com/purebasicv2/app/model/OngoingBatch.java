package com.purebasicv2.app.model;

import com.google.gson.annotations.Expose;

public class OngoingBatch {
    public int id;
    public int batch_id;
    public String title;
    public String fild_1;
    public String fild_2;
    public String fild_3;
    public String fild_4;
    public String fild_5;
    public String fild_6;
    public String fild_7;
    public String fild_8;
    public String fild_9;
    public String fild_10;
    public String link;
    public int status;
    public Object created_at;
    public String updated_at;
    public String promotion_video;
    public int total_video;
    public int total_student;
    public int total_exam;
    public String total_time;
    public String cover_image;

    @Expose(serialize = false, deserialize = false)
    public boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(int batch_id) {
        this.batch_id = batch_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFild_1() {
        return fild_1;
    }

    public void setFild_1(String fild_1) {
        this.fild_1 = fild_1;
    }

    public String getFild_2() {
        return fild_2;
    }

    public void setFild_2(String fild_2) {
        this.fild_2 = fild_2;
    }

    public String getFild_3() {
        return fild_3;
    }

    public void setFild_3(String fild_3) {
        this.fild_3 = fild_3;
    }

    public String getFild_4() {
        return fild_4;
    }

    public void setFild_4(String fild_4) {
        this.fild_4 = fild_4;
    }

    public String getFild_5() {
        return fild_5;
    }

    public void setFild_5(String fild_5) {
        this.fild_5 = fild_5;
    }

    public String getFild_6() {
        return fild_6;
    }

    public void setFild_6(String fild_6) {
        this.fild_6 = fild_6;
    }

    public String getFild_7() {
        return fild_7;
    }

    public void setFild_7(String fild_7) {
        this.fild_7 = fild_7;
    }

    public String getFild_8() {
        return fild_8;
    }

    public void setFild_8(String fild_8) {
        this.fild_8 = fild_8;
    }

    public String getFild_9() {
        return fild_9;
    }

    public void setFild_9(String fild_9) {
        this.fild_9 = fild_9;
    }

    public String getFild_10() {
        return fild_10;
    }

    public void setFild_10(String fild_10) {
        this.fild_10 = fild_10;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Object created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPromotion_video() {
        return promotion_video;
    }

    public void setPromotion_video(String promotion_video) {
        this.promotion_video = promotion_video;
    }

    public int getTotal_video() {
        return total_video;
    }

    public int getTotal_student() {
        return total_student;
    }

    public int getTotal_exam() {
        return total_exam;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_video(int total_video) {
        this.total_video = total_video;
    }

    public void setTotal_student(int total_student) {
        this.total_student = total_student;
    }

    public void setTotal_exam(int total_exam) {
        this.total_exam = total_exam;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }
}
