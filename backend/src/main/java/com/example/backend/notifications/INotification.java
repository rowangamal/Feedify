package com.example.backend.notifications;

public interface INotification {
    public void sendNotificationLike(String message , String image , long NotifiedUserId);
    public void sendNotificationComment(String message , String image , long NotifiedUserId);
    public void sendNotificationRepost(String message , String image , long NotifiedUserId);
    public void sendNotificationFollow(String message , String image , long NotifiedUserId);
    public void sendNotificationReport(String message , String image);
}
