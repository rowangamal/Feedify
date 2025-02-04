package com.example.backend.enums;

public class TableColNames {
    // --------------------- User Table -----------------------
    public static final String USER_TABLE = "user";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_FNAME = "fname";
    public static final String USER_LNAME = "lname";
    public static final String USER_BIRTHDATE = "birthdate";
    public static final String USER_GENDER = "gender";
    public static final String USER_PICTUREURL = "pic_url";
    public static final String USER_IS_VERIFIED = "isVerified";
    public static final String USER_VERIFICATION_CODE = "verificationCode";
    public static final String USER_CODE_EXPIRATION_DATE = "codeExpirationDate";

    // --------------------- OTP Table -----------------------
    public static final String OTP_TABLE = "otp";
    public static final String OTP = "reset_password_otp";
    public static final String OTP_EXPIRATION_DATE = "reset_otp_expiration";

    // --------------------- Admin Table -----------------------
    public static final String ADMIN_TABLE = "admin";

    // --------------------- Post Table -----------------------
    public static final String POST_TABLE = "post";
    public static final String POST_USER_ID = "user_id";
    public static final String POST_TOPIC_ID = "topic_id";
    public static final String POST_CONTENT = "text";
    public static final String POST_DATE = "createdAt";
    public static final String POST_LIKES_COUNT = "likesCount";
    public static final String POST_COMMENTS_COUNT = "commentsCount";
    public static final String POST_REPOST_COUNT = "repostsCount";
    public static final String POST_IMAGE = "image";


    // --------------------- Topic Table -----------------------
    public static final String TOPIC_TABLE = "topic";
    public static final String TOPIC_NAME = "name";

    // ---------------------  OF_Type Table -----------------------

    public static final String OF_TYPE_TABLE = "OF_Type";
    public static final String OF_TOPIC_POST_ID = "post_id";
    public static final String OF_TOPIC_TOPIC_ID = "topic_id";

    // ---------------------  User_Interest Table -----------------------
    public static final String USER_INTEREST_TABLE = "user_interest";
    public static final String USER_INTEREST_USER_ID = "user_id";
    public static final String USER_INTEREST_TOPIC_ID = "topic_id";

    // ---------------------  Follows Table -----------------------
    public static final String FOLLOWS_TABLE = "follows";
    public static final String FOLLOWS_FOLLOWER_ID = "follower_id";
    public static final String FOLLOWS_FOLLOWING_ID = "following_id";

    // ---------------------  Comment Table -----------------------
    public static final String COMMENT_TABLE = "comment";
    public static final String COMMENT_USER_ID = "user_id";
    public static final String COMMENT_POST_ID = "post_id";
    public static final String COMMENT_CONTENT = "content";
    public static final String COMMENT_DATE = "createdAt";

    // ---------------------  Like Table -----------------------
    public static final String LIKE_TABLE = "likes";
    public static final String LIKE_USER_ID = "user_id";
    public static final String LIKE_POST_ID = "post_id";
    public static final String LIKE_DATE = "createdAt";

    // ---------------------  Repost Table -----------------------
    public static final String REPOST_TABLE = "repost";
    public static final String REPOST_USER_ID = "user_id";
    public static final String REPOST_POST_ID = "post_id";
    public static final String REPOST_DATE = "createdAt";

    // ---------------------  User Report Table -----------------------
    public static final String USER_REPORT_TABLE = "user_report";
    public static final String USER_REPORT_REPORTER_ID = "reporter_id";
    public static final String USER_REPORT_REPORTED_ID = "reported_id";
    public static final String USER_REPORT_DATE = "createdAt";
    public static final String USER_REPORT_REASON = "reason";

    // ---------------------  POST Report Table -----------------------
    public static final String POST_REPORT_TABLE = "post_report";
    public static final String POST_REPORT_REPORTER_ID = "reporter_id";
    public static final String POST_REPORT_POST_ID = "post_id";
    public static final String POST_REPORT_DATE = "createdAt";
    public static final String POST_REPORT_REASON = "reason";

    // --------------------- Foreign Keys Renaming -----------------------
    public static final String USER_ID = "user_id";


}
