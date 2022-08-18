package com.purebasicv2.app.model;

import java.util.List;

public class ExamHistoryResponse {


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
        private List<ExamInfo> data = null;
        private String first_page_url;
        private Integer from;
        private Integer last_page;
        private String last_page_url;
        private String next_page_url;
        private String path;
        private Integer per_page;
        private String prev_page_url;
        private Integer to;
        private Integer total;

        public Integer getCurrentPage() {
            return current_page;
        }

        public void setCurrentPage(Integer currentPage) {
            this.current_page = currentPage;
        }

        public List<ExamInfo> getData() {
            return data;
        }

        public void setData(List<ExamInfo> data) {
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

        public String getPrevPageUrl() {
            return prev_page_url;
        }

        public void setPrevPageUrl(String prevPageUrl) {
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

    public class ExamInfo {

        private Integer id;
        private Integer total_questions;
        private Integer answered_questions;
        private Integer unanswered_questions;
        private Integer right_answers;
        private double point;
        private Integer wrong_answers;
        private Integer student_id;
        private Integer modeltest_id;
        private Integer status;
        private Integer action_status;
        private String created_at;
        private Modeltest modeltest;

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

        public double getPoint() {
            return point;
        }

        public void setPoint(double point) {
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

        public Modeltest getModeltest() {
            return modeltest;
        }

        public void setModeltest(Modeltest modeltest) {
            this.modeltest = modeltest;
        }
    }


    public class Modeltest {

        private Integer id;
        private String exam_type;
        private String name;
        private Object subject;
        private Integer status;
        private Integer exam_in_minutes;
        private String ex_time;
        private String solve_shet;
        private String created_at;
        private String updated_at;

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

        public Object getSubject() {
            return subject;
        }

        public void setSubject(Object subject) {
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
            this.ex_time = exTime;
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

    }
}
