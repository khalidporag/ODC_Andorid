
package com.purebasicv2.app.model;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("play_store_key")
    private String apiKey;
    @SerializedName("student_info")
    private StudentInfo mStudentInfo;

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String apiToken) {
        mApiToken = apiToken;
    }

    public StudentInfo getStudentInfo() {
        return mStudentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        mStudentInfo = studentInfo;
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
