package com.purebasicv2.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class LectureRoot {
    @SerializedName("data")
    @Expose
    HashMap<String, List<Lecture>> lectureHashMap = new HashMap<>();

    public HashMap<String, List<Lecture>> getLectureHashMap() {
        return lectureHashMap;
    }

    public void setLectureHashMap(HashMap<String, List<Lecture>> lectureHashMap) {
        this.lectureHashMap = lectureHashMap;
    }
}
