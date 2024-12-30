package com.example.backend.timeCreation;

import java.sql.Timestamp;

public class TimeCreationBuilder {
    public static String getCreationTime(Timestamp time) {
        if (time == null) return "Unknown";
        long diff = (System.currentTimeMillis() - time.getTime()) / 1000;
        if (diff < 60) return "Just now";
        if (diff < 3600) return diff / 60 + " minutes ago";
        if (diff < 86400) return diff / 3600 + " hours ago";
        if (diff < 604800) return diff / 86400 + " days ago";
        if (diff < 2592000) return diff / 604800 + " weeks ago";
        if (diff < 31536000) return diff / 2592000 + " months ago";
        return diff / 31536000 + " years ago";
    }
}
