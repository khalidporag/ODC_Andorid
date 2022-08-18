package com.purebasicv2.app.model;

import java.util.List;

public class ResultList {

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

        private ExamInfo exam_info;
        private Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public ExamInfo getExamInfo() {
            return exam_info;
        }

        public void setExamInfo(ExamInfo examInfo) {
            this.exam_info = examInfo;
        }

    }

    public class Result {
        private Integer id;
        private Integer total_questions;
        private Integer answered_questions;
        private Integer unanswered_questions;
        private Integer right_answers;
        private Double point;
        private Double total_mark;

        private Integer wrong_answers;
        private Integer student_id;
        private Integer modeltest_id;
        private Integer status;
        private Integer action_status;
        private String created_at;

        public Double getTotal_mark() {
            return total_mark;
        }

        public void setTotal_mark(Double total_mark) {
            this.total_mark = total_mark;
        }

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

    }

    public class Question {

        private Integer id;
        private Integer modeltest_id;
        private String question;
        private String detailss;
        private Integer is_multi;
        private Integer status;
        private Object created_at;

        private Object updated_at;
        private List<Option> options = null;
        private List<String> correct_answer = null;

        public List<String> getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(List<String> correct_answer) {
            this.correct_answer = correct_answer;
        }

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
        private Integer is_answered;

        public Integer getIs_answered() {
            return is_answered;
        }

        public void setIs_answered(Integer is_answered) {
            this.is_answered = is_answered;
        }

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

    public class ExamInfo {

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
        private List<Question> questions = null;

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
            this.solve_shet = solve_shet;
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

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

    }
}
