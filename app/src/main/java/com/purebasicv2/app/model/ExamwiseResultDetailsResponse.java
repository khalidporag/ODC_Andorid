package com.purebasicv2.app.model;

import java.util.List;

public class ExamwiseResultDetailsResponse {

    private Boolean success;
    private String status;
    private Integer error_code;

    private String message;

    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        private Integer current_page;
        private List<ResultData> data = null;
        private String first_page_url;
        private Integer from;
        private Integer last_page;
        private String last_page_url;
        private String next_page_url;
        private String path;
        private Integer per_page;
        private Object prev_page_url;
        private Integer to;
        private Integer total;

        public Integer getCurrentPage() {
            return current_page;
        }

        public void setCurrentPage(Integer currentPage) {
            this.current_page = currentPage;
        }

        public List<ResultData> getData() {
            return data;
        }

        public void setData(List<ResultData> data) {
            this.data = data;
        }

        public String getFirstPageUrl() {
            return first_page_url;
        }

        public void setFirstPageUrl(String firstPageUrl) {
            this.first_page_url = firstPageUrl;
        }

        public Integer getFrom() {
            return from;
        }

        public void setFrom(Integer from) {
            this.from = from;
        }

        public Integer getLastPage() {
            return last_page;
        }

        public void setLastPage(Integer lastPage) {
            this.last_page = lastPage;
        }

        public String getLastPageUrl() {
            return last_page_url;
        }

        public void setLastPageUrl(String lastPageUrl) {
            this.last_page_url = lastPageUrl;
        }

        public String getNextPageUrl() {
            return next_page_url;
        }

        public void setNextPageUrl(String nextPageUrl) {
            this.next_page_url = nextPageUrl;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getPerPage() {
            return per_page;
        }

        public void setPerPage(Integer perPage) {
            this.per_page = perPage;
        }

        public Object getPrevPageUrl() {
            return prev_page_url;
        }

        public void setPrevPageUrl(Object prevPageUrl) {
            this.prev_page_url = prevPageUrl;
        }

        public Integer getTo() {
            return to;
        }

        public void setTo(Integer to) {
            this.to = to;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }


    public class ResultData {

        private Integer id;
        private Integer total_questions;
        private Integer answered_questions;
        private Integer unanswered_questions;
        private Integer right_answers;
        private Double point;
        private Integer wrong_answers;
        private Integer student_id;
        private Integer modeltest_id;

        private Integer status;
        private Integer action_status;
        private String created_at;
        private Students students;
        private Integer rank;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getTotalQuestions() {
            return total_questions;
        }

        public void setTotalQuestions(Integer totalQuestions) {
            this.total_questions = totalQuestions;
        }

        public Integer getAnsweredQuestions() {
            return answered_questions;
        }

        public void setAnsweredQuestions(Integer answeredQuestions) {
            this.answered_questions = answeredQuestions;
        }

        public Integer getUnansweredQuestions() {
            return unanswered_questions;
        }

        public void setUnansweredQuestions(Integer unansweredQuestions) {
            this.unanswered_questions = unansweredQuestions;
        }

        public Integer getRightAnswers() {
            return right_answers;
        }

        public void setRightAnswers(Integer rightAnswers) {
            this.right_answers = rightAnswers;
        }

        public Double getPoint() {
            return point;
        }

        public void setPoint(Double point) {
            this.point = point;
        }

        public Integer getWrongAnswers() {
            return wrong_answers;
        }

        public void setWrongAnswers(Integer wrongAnswers) {
            this.wrong_answers = wrongAnswers;
        }

        public Integer getStudentId() {
            return student_id;
        }

        public void setStudentId(Integer studentId) {
            this.student_id = studentId;
        }

        public Integer getModeltestId() {
            return modeltest_id;
        }

        public void setModeltestId(Integer modeltestId) {
            this.modeltest_id = modeltestId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getActionStatus() {
            return action_status;
        }

        public void setActionStatus(Integer actionStatus) {
            this.action_status = actionStatus;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String createdAt) {
            this.created_at = createdAt;
        }

        public Students getStudents() {
            return students;
        }

        public void setStudents(Students students) {
            this.students = students;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }
    }



    public class Students {

        private Integer id;
        private String student_id;
        private String name;
        private Integer taka;
        private String email;
        private String mobile;
        private String gender;
        private String batch_id;
        private String birth;
        private String BMDC;
        private String medical;
        private String batch;
        private String sessionn;
        private String levell;
        private String position;
        private String qualification;
        private String country;
        private String fb;
        private String address;
        private String photo;
        private String otp;
        private String api_token;
        private Integer is_approve;
        private String password;
        private String status;
        private String created_at;
        private String updated_at;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getStudentId() {
            return student_id;
        }

        public void setStudentId(String studentId) {
            this.student_id = studentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTaka() {
            return taka;
        }

        public void setTaka(Integer taka) {
            this.taka = taka;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBatchId() {
            return batch_id;
        }

        public void setBatchId(String batchId) {
            this.batch_id = batchId;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getBmdc() {
            return BMDC;
        }

        public void setBmdc(String bmdc) {
            this.BMDC = bmdc;
        }

        public String getMedical() {
            return medical;
        }

        public void setMedical(String medical) {
            this.medical = medical;
        }

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getSessionn() {
            return sessionn;
        }

        public void setSessionn(String sessionn) {
            this.sessionn = sessionn;
        }

        public String getLevell() {
            return levell;
        }

        public void setLevell(String levell) {
            this.levell = levell;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getFb() {
            return fb;
        }

        public void setFb(String fb) {
            this.fb = fb;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getApiToken() {
            return api_token;
        }

        public void setApiToken(String apiToken) {
            this.api_token = apiToken;
        }

        public Integer getIsApprove() {
            return is_approve;
        }

        public void setIsApprove(Integer isApprove) {
            this.is_approve = isApprove;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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
}
