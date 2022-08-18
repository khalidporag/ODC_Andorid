package com.purebasicv2.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentHistoryResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error_code")
    @Expose
    private Integer error_code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return error_code;
    }

    public void setErrorCode(Integer errorCode) {
        this.error_code = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }



    public class Batch {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("plan")
        @Expose
        private String plan;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("graduation")
        @Expose
        private String graduation;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("subscription_days")
        @Expose
        private Integer subscription_days;
        @SerializedName("ammount")
        @Expose
        private String ammount;
        @SerializedName("yr")
        @Expose
        private String yr;
        @SerializedName("batch_id")
        @Expose
        private String batch_id;
        @SerializedName("show")
        @Expose
        private Integer show;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String created_at;
        @SerializedName("updated_at")
        @Expose
        private String updated_at;

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

        public String getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updated_at = updatedAt;
        }

    }



    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("student_id")
        @Expose
        private Integer student_id;
        @SerializedName("batch_id")
        @Expose
        private Integer batch_id;
        @SerializedName("transaction")
        @Expose
        private String transaction;
        @SerializedName("mar")
        @Expose
        private String mar;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("reference_id")
        @Expose
        private Integer reference_id;
        @SerializedName("is_approve")
        @Expose
        private Integer is_approve;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("approved_at")
        @Expose
        private Object approved_at;
        @SerializedName("created_at")
        @Expose
        private String created_at;
        @SerializedName("updated_at")
        @Expose
        private String updated_at;
        @SerializedName("batch")
        @Expose
        private Batch batch;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStudentId() {
            return student_id;
        }

        public void setStudentId(Integer studentId) {
            this.student_id = studentId;
        }

        public Integer getBatchId() {
            return batch_id;
        }

        public void setBatchId(Integer batchId) {
            this.batch_id = batchId;
        }

        public String getTransaction() {
            return transaction;
        }

        public void setTransaction(String transaction) {
            this.transaction = transaction;
        }

        public String getMar() {
            return mar;
        }

        public void setMar(String mar) {
            this.mar = mar;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getReferenceId() {
            return reference_id;
        }

        public void setReferenceId(Integer referenceId) {
            this.reference_id = referenceId;
        }

        public Integer getIsApprove() {
            return is_approve;
        }

        public void setIsApprove(Integer isApprove) {
            this.is_approve = isApprove;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getApprovedAt() {
            return approved_at;
        }

        public void setApprovedAt(Object approvedAt) {
            this.approved_at = approvedAt;
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

        public Batch getBatch() {
            return batch;
        }

        public void setBatch(Batch batch) {
            this.batch = batch;
        }

    }
}
