
package com.purebasicv2.app.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StudentInfo {

    @SerializedName("address")
    private Object mAddress;
    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("BMDC")
    private Object mBMDC;
    @SerializedName("batch")
    private Object mBatch;
    @SerializedName("batch_id")
    private Object mBatchId;
    @SerializedName("birth")
    private Object mBirth;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fb")
    private Object mFb;
    @SerializedName("gender")
    private Object mGender;
    @SerializedName("id")
    private int mId;
    @SerializedName("is_approve")
    private Long mIsApprove;
    @SerializedName("levell")
    private Object mLevell;
    @SerializedName("medical")
    private Object mMedical;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("otp")
    private Object mOtp;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("position")
    private String mPosition;
    @SerializedName("qualification")
    private String mQualification;
    @SerializedName("sessionn")
    private Object mSessionn;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("student_id")
    private String mGeneratedSId;
    @SerializedName("taka")
    private Object mTaka;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("batch_count")
    private int mBatchCount = 0;

    public int getmBatchCount() {
        return mBatchCount;
    }

    public void setmBatchCount(int mBatchCount) {
        this.mBatchCount = mBatchCount;
    }

    public Object getAddress() {
        return mAddress;
    }

    public void setAddress(Object address) {
        mAddress = address;
    }

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String apiToken) {
        mApiToken = apiToken;
    }

    public Object getBMDC() {
        return mBMDC;
    }

    public void setBMDC(Object bMDC) {
        mBMDC = bMDC;
    }

    public Object getBatch() {
        return mBatch;
    }

    public void setBatch(Object batch) {
        mBatch = batch;
    }

    public Object getBatchId() {
        return mBatchId;
    }

    public void setBatchId(Object batchId) {
        mBatchId = batchId;
    }

    public Object getBirth() {
        return mBirth;
    }

    public void setBirth(Object birth) {
        mBirth = birth;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Object createdAt) {
        mCreatedAt = createdAt;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Object getFb() {
        return mFb;
    }

    public void setFb(Object fb) {
        mFb = fb;
    }

    public Object getGender() {
        return mGender;
    }

    public void setGender(Object gender) {
        mGender = gender;
    }

    public int getId() {
        return mId;
    }


    public Long getIsApprove() {
        return mIsApprove;
    }

    public void setIsApprove(Long isApprove) {
        mIsApprove = isApprove;
    }

    public Object getLevell() {
        return mLevell;
    }

    public void setLevell(Object levell) {
        mLevell = levell;
    }

    public Object getMedical() {
        return mMedical;
    }

    public void setMedical(Object medical) {
        mMedical = medical;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getOtp() {
        return mOtp;
    }

    public void setOtp(Object otp) {
        mOtp = otp;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public String getQualification() {
        return mQualification;
    }

    public void setQualification(String qualification) {
        mQualification = qualification;
    }

    public Object getSessionn() {
        return mSessionn;
    }

    public void setSessionn(Object sessionn) {
        mSessionn = sessionn;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Object getStudentId() {
        return mId;
    }

    public String getmGeneratedSId() {
        return mGeneratedSId;
    }

    public void setmGeneratedSId(String mGeneratedSId) {
        this.mGeneratedSId = mGeneratedSId;
    }

    public Object getTaka() {
        return mTaka;
    }

    public void setTaka(Object taka) {
        mTaka = taka;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "mAddress=" + mAddress +
                ", mApiToken='" + mApiToken + '\'' +
                ", mBMDC=" + mBMDC +
                ", mBatch=" + mBatch +
                ", mBatchId=" + mBatchId +
                ", mBirth=" + mBirth +
                ", mCountry='" + mCountry + '\'' +
                ", mCreatedAt=" + mCreatedAt +
                ", mEmail='" + mEmail + '\'' +
                ", mFb=" + mFb +
                ", mGender=" + mGender +
                ", mId=" + mId +
                ", mIsApprove=" + mIsApprove +
                ", mLevell=" + mLevell +
                ", mMedical=" + mMedical +
                ", mMobile='" + mMobile + '\'' +
                ", mName='" + mName + '\'' +
                ", mOtp=" + mOtp +
                ", mPassword='" + mPassword + '\'' +
                ", mPhoto='" + mPhoto + '\'' +
                ", mPosition='" + mPosition + '\'' +
                ", mQualification='" + mQualification + '\'' +
                ", mSessionn=" + mSessionn +
                ", mStatus='" + mStatus + '\'' +
                ", mStudentId=" + mGeneratedSId +
                ", mTaka=" + mTaka +
                ", mUpdatedAt='" + mUpdatedAt + '\'' +
                '}';
    }
}
