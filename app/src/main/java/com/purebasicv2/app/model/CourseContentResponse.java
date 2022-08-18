package com.purebasicv2.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseContentResponse {


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


    public class BatchInfo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("batch_id")
        @Expose
        private Integer batchId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("fild_1")
        @Expose
        private String fild_1;
        @SerializedName("fild_2")
        @Expose
        private String fild_2;
        @SerializedName("fild_3")
        @Expose
        private String fild_3;
        @SerializedName("fild_4")
        @Expose
        private String fild_4;
        @SerializedName("fild_5")
        @Expose
        private String fild_5;
        @SerializedName("fild_6")
        @Expose
        private String fild_6;
        @SerializedName("fild_7")
        @Expose
        private String fild_7;
        @SerializedName("fild_8")
        @Expose
        private String fild_8;
        @SerializedName("fild_9")
        @Expose
        private String fild_9;
        @SerializedName("fild_10")
        @Expose
        private String fild_10;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private Object created_at;
        @SerializedName("updated_at")
        @Expose
        private String updated_at;
        @SerializedName("promotion_video")
        @Expose
        private String promotion_video;
        @SerializedName("cover_image")
        @Expose
        private Object cover_image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getBatchId() {
            return batchId;
        }

        public void setBatchId(Integer batchId) {
            this.batchId = batchId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFild1() {
            return fild_1;
        }

        public void setFild1(String fild1) {
            this.fild_1 = fild1;
        }

        public String getFild2() {
            return fild_2;
        }

        public void setFild2(String fild2) {
            this.fild_2 = fild2;
        }

        public String getFild3() {
            return fild_3;
        }

        public void setFild3(String fild3) {
            this.fild_3 = fild3;
        }

        public String getFild4() {
            return fild_4;
        }

        public void setFild4(String fild4) {
            this.fild_4 = fild4;
        }

        public String getFild5() {
            return fild_5;
        }

        public void setFild5(String fild5) {
            this.fild_5 = fild5;
        }

        public String getFild6() {
            return fild_6;
        }

        public void setFild6(String fild6) {
            this.fild_6 = fild6;
        }

        public String getFild7() {
            return fild_7;
        }

        public void setFild7(String fild7) {
            this.fild_7 = fild7;
        }

        public String getFild8() {
            return fild_8;
        }

        public void setFild8(String fild8) {
            this.fild_8 = fild8;
        }

        public String getFild9() {
            return fild_9;
        }

        public void setFild9(String fild9) {
            this.fild_9 = fild9;
        }

        public String getFild10() {
            return fild_10;
        }

        public void setFild10(String fild10) {
            this.fild_10 = fild10;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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

        public String getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updated_at = updatedAt;
        }

        public String getPromotionVideo() {
            return promotion_video;
        }

        public void setPromotionVideo(String promotionVideo) {
            this.promotion_video = promotionVideo;
        }

        public Object getCoverImage() {
            return cover_image;
        }

        public void setCoverImage(Object coverImage) {
            this.cover_image = coverImage;
        }

    }





    public class Chapter {

        @SerializedName("chapter_name")
        @Expose
        private String chapter_name;
        @SerializedName("lecture_list")
        @Expose
        private List<Lecture> lecture_list = null;

        public String getChapterName() {
            return chapter_name;
        }

        public void setChapterName(String chapterName) {
            this.chapter_name = chapterName;
        }

        public List<Lecture> getLectureList() {
            return lecture_list;
        }

        public void setLectureList(List<Lecture> lectureList) {
            this.lecture_list = lectureList;
        }

    }

    public class Data {

        @SerializedName("batch_info")
        @Expose
        private BatchInfo batch_info;
        @SerializedName("lecture_details")
        @Expose
        private List<LectureDetail> lecture_details = null;

        public BatchInfo getBatchInfo() {
            return batch_info;
        }

        public void setBatchInfo(BatchInfo batchInfo) {
            this.batch_info = batchInfo;
        }

        public List<LectureDetail> getLectureDetails() {
            return lecture_details;
        }

        public void setLectureDetails(List<LectureDetail> lectureDetails) {
            this.lecture_details = lectureDetails;
        }

    }

    public class Lecture {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cat_id")
        @Expose
        private Integer cat_id;
        @SerializedName("cp_id")
        @Expose
        private Integer cp_id;
        @SerializedName("member_type")
        @Expose
        private String member_type;
        @SerializedName("date_time")
        @Expose
        private String date_time;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("levell")
        @Expose
        private String levell;
        @SerializedName("pdf")
        @Expose
        private Object pdf;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("links")
        @Expose
        private String links;
        @SerializedName("v_link")
        @Expose
        private Object v_link;
        @SerializedName("lc_video")
        @Expose
        private Object lc_video;
        @SerializedName("video_id")
        @Expose
        private Object video_id;
        @SerializedName("thumb")
        @Expose
        private Object thumb;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("chapter_name")
        @Expose
        private String chapterName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCatId() {
            return cat_id;
        }

        public void setCatId(Integer catId) {
            this.cat_id = catId;
        }

        public Integer getCpId() {
            return cp_id;
        }

        public void setCpId(Integer cpId) {
            this.cp_id = cpId;
        }

        public String getMemberType() {
            return member_type;
        }

        public void setMemberType(String memberType) {
            this.member_type = memberType;
        }

        public String getDateTime() {
            return date_time;
        }

        public void setDateTime(String dateTime) {
            this.date_time = dateTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getLevell() {
            return levell;
        }

        public void setLevell(String levell) {
            this.levell = levell;
        }

        public Object getPdf() {
            return pdf;
        }

        public void setPdf(Object pdf) {
            this.pdf = pdf;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }

        public Object getvLink() {
            return v_link;
        }

        public void setvLink(Object vLink) {
            this.v_link = vLink;
        }

        public Object getLcVideo() {
            return lc_video;
        }

        public void setLcVideo(Object lcVideo) {
            this.lc_video = lcVideo;
        }

        public Object getVideoId() {
            return video_id;
        }

        public void setVideoId(Object videoId) {
            this.video_id = videoId;
        }

        public Object getThumb() {
            return thumb;
        }

        public void setThumb(Object thumb) {
            this.thumb = thumb;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

    }

    public class LectureDetail {

        @SerializedName("subject_name")
        @Expose
        private String subject_name;
        @SerializedName("chapter_list")
        @Expose
        private List<Chapter> chapter_list = null;

        public boolean isExpanded = false;

        public String getSubjectName() {
            return subject_name;
        }

        public void setSubjectName(String subjectName) {
            this.subject_name = subjectName;
        }

        public List<Chapter> getChapterList() {
            return chapter_list;
        }

        public void setChapterList(List<Chapter> chapterList) {
            this.chapter_list = chapterList;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }
    }
}
