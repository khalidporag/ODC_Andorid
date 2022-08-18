package com.purebasicv2.app.constant;

public class Constants {
    public static final String ROOT_URL_NEW = "https://www.purebasic.com.bd/api/";
    public static final String ROOT_URL = "https://api.purebasic.com.bd/api/";//"https://api.purebasic.com.bd/api/";
    public static final String API_VERSION = "v1/";
    public static final String VENDOR_API = ROOT_URL + API_VERSION;
    public static final String APP_API = ROOT_URL + API_VERSION + "app-api.php";
    public static final String URL_LOGIN = VENDOR_API + "user_login.php";
    public static final String REGISTER_API = ROOT_URL + API_VERSION + "register.php";
    public static final String API_USER_DATA = ROOT_URL + API_VERSION + "user_data.php";
    public static final String USER_PROFILE = VENDOR_API + "user_profile.php";
    public static final String UPDATE_PROFILE = VENDOR_API + "update_profile.php";
    public static final String UPLOAD_AVATAR = VENDOR_API + "upload_profile_picture.php";
    public static final String PHONE_CHECK = ROOT_URL + API_VERSION + "mobile_check.php?";
    public static final String CATEGORY_LECTURE_SHEET = VENDOR_API + "lecture_sheet.php";
    public static final String LECTURE_VIEW = VENDOR_API + "lecture_view.php";
    public static final String MODEL_TEST_LIST = VENDOR_API + "model_test_list.php";
    public static final String MODEL_TEST_QUESTIONS = VENDOR_API + "model_test_question.php";
    public static final String OPTION_SUBMIT = VENDOR_API + "option_submit.php";
    public static final String MODEL_TEST_QUESTIONS_RESULT = VENDOR_API + "model_test_result.php";
    public static final String MODEL_TEST_HISTORY = VENDOR_API + "model_test_joined.php";
    public static final String PAYMENT_API = VENDOR_API + "pay_api.php";
    public static final String PAYMENT_INFO = VENDOR_API + "pay_info.php";
    public static final String MEMBERSHIP_ITEMS = VENDOR_API + "membership_item.php";
    public static final String MODEL_TEST_RESULT = VENDOR_API + "model_test_batch.php";
    public static final String MODEL_TEST_MERIT_VIEW = VENDOR_API + "model_test_merit_view.php";
    public static final String MAIL_API = VENDOR_API + "mail.php";

    public static final String LOGIN = ROOT_URL_NEW + "login/";
    public static final String REGISTER = ROOT_URL_NEW + "register/";
    public static final String OTP_VERIFICATION = ROOT_URL_NEW + "otp_verify/";
    public static final String RESEND_OTP = ROOT_URL_NEW + "resend_otp/";
    public static final String ENROLLED_BATCH = ROOT_URL_NEW + "batch_by_student?api_test=false";
    public static final String SUBJECTS = ROOT_URL_NEW + "subject_by_batch?api_test=false";
    public static final String LECTURES = ROOT_URL_NEW + "chapter_lecture_by_subject?api_test=false";
    public static final String ONGOING_BATCH = ROOT_URL_NEW + "ongoing_batch?api_test=false";
    public static final String REQUEST_ENROLL_BATCH = ROOT_URL_NEW + "enroll_batch?api_test=false";
    public static final String NEW_PAYMENT = ROOT_URL_NEW + "make_payment?api_test=false";
    public static final String DOWNLOAD_FOLDER = ".pb_saved";


    public static final String EXAM_LIST_ALL = ROOT_URL_NEW+"spacialmodeltest-exam?api_test=false";
    public static final String QUESTION_LIST = ROOT_URL_NEW+"spacialmodeltest-exam/";
    public static final String EXAM_BY_BATCH = ROOT_URL_NEW+"exam_by_batch/";
    public static final String EXAM_SUBMIT = ROOT_URL_NEW+"submit_model_test?api_test=false";
    public static final String EXAM_RESULT = ROOT_URL_NEW+"seeExamResult?api_test=false";
    public static final String EXAM_WISE_RESULT = ROOT_URL_NEW+"examRank?api_test=false";
    public static final String EXAM_HISTORY = ROOT_URL_NEW+"my_exam_history?api_test=false";
    public static final String EXAM_WISE_RESULT_DETAILS = ROOT_URL_NEW+"exam/point/list/";

    public static final String PAYMENT_HISTORY = ROOT_URL_NEW+"payment_history?api_test=false";

    public static final String PROFILE_UPDATE = ROOT_URL_NEW + "updateStudent/";
    public static final String PROFILE_INFO = ROOT_URL_NEW + "student/";
    public static final String FORGOT_PASS_OTP_REQ = ROOT_URL_NEW + "forgot_pass";
    public static final String RESET_PASS = ROOT_URL_NEW + "resetPassword";
    public static final String COURSE_CONTENT = ROOT_URL_NEW + "batch_details/";
    public static final String BOOKLIST = ROOT_URL_NEW + "books?api_test=false";
    public static final String NOTICE = ROOT_URL_NEW + "notice?api_test=true&";
    public static final String NOMINATIM_URL="https://nominatim.openstreetmap.org/reverse?addressdetails=1&format=json&zoom=18";



    public enum PaymentMethod {
        BKASH, ROCKET, NAGAD, DBBl, CITY_BANK, BRAC_BANK
    }


}
