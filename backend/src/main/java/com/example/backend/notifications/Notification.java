package com.example.backend.notifications;

import com.example.backend.dtos.MessageNotification;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class Notification implements INotification {

    private final SimpMessageSendingOperations messagingTemplate;
    @Autowired
    public Notification(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @Autowired
    UserService userService;

    @Override
    public void sendNotificationLike(String message , String image , long NotifiedUserId) {
        MessageNotification messageNotification = new MessageNotification(message , image);
        messagingTemplate.convertAndSend("/topic/like/"+NotifiedUserId, messageNotification);
    }

    @Override
    public void sendNotificationComment(String message , String image , long NotifiedUserId) {
        MessageNotification messageNotification = new MessageNotification(message , image);
        messagingTemplate.convertAndSend("/topic/comment/"+NotifiedUserId, messageNotification);
    }

    @Override
    public void sendNotificationRepost(String message , String image, long NotifiedUserId) {
        MessageNotification messageNotification = new MessageNotification(message , image);
        messagingTemplate.convertAndSend("/topic/repost/"+NotifiedUserId, messageNotification);
    }

    @Override
    public void sendNotificationFollow(String message , String image , long NotifiedUserId) {
        MessageNotification messageNotification = new MessageNotification(message , image);
        messagingTemplate.convertAndSend("/topic/follow/"+NotifiedUserId, messageNotification);    }

    @Override
    public void sendNotificationReport(String message , String image) {
        MessageNotification messageNotification = new MessageNotification(message , image);
        messagingTemplate.convertAndSend("/topic/report", messageNotification);
    }
}
