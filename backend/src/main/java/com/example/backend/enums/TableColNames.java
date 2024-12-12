package com.example.backend.enums;

public class TableColNames {
    // --------------------- User Table -----------------------
    public static final String USER_TABLE = "user";
    public  static  final String USER_USERNAME = "username";
    public  static  final String USER_PASSWORD = "password";
    public  static  final String USER_EMAIL = "email";
    public  static  final String USER_FNAME = "fname";
    public  static  final String USER_LNAME = "lname";
    public  static  final String USER_BIRTHDATE = "birthdate";
    public  static  final String USER_GENDER = "gender";
    public  static  final String USER_PICTUREURL= "pic_url";
    public  static  final String USER_RESET_PASSWORD_OTP= "reset_password_otp";


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
    public static final String OF_TOPIC_TOPIC_ID = "topic_id" ;

    // --------------------- Foreign Keys Renaming -----------------------
    public static final String USER_ID = "user_id";



}
