package com.example.backend.notification;

import com.example.backend.dtos.MessageNotification;
import com.example.backend.notifications.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class NotificationTest {
    @InjectMocks
    private Notification notification;

    @Mock
    private SimpMessageSendingOperations messagingTemplate;
    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @Test
    void setNotificationLikeInNormalBehaviouralTest() {
        int expected = 1;
        notification.sendNotificationLike("message", "image", 1);
        assertEquals( expected, 1);
    }
    @ParameterizedTest
    @CsvSource({
            "'User liked your post!', 'profile1.jpg', 1",
            "'User loved your post!', 'profile2.png', 2"
    })
    void SendNotificationLikeToCheckTheMatchingTest(String message, String image, long notifiedUserId) {
        notification.sendNotificationLike(message, image, notifiedUserId);
        ArgumentCaptor<String> destinationCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MessageNotification> payloadCapture = ArgumentCaptor.forClass(MessageNotification.class);
        verify(messagingTemplate, times(1))
                .convertAndSend(destinationCapture.capture(), payloadCapture.capture());

        assertEquals("/topic/like/" + notifiedUserId, destinationCapture.getValue());
        MessageNotification capturedNotification = payloadCapture.getValue();
        assertEquals(message, capturedNotification.getMessage());
        assertEquals(image, capturedNotification.getImage());
    }
    @Test
    void setNotificationCommentInNormalBehaviouralTest() {
        int expected = 1;
        notification.sendNotificationComment("message", "image", 1);
        assertEquals( expected, 1);
    }
    @ParameterizedTest
    @CsvSource({
            "'User comment on your post!', 'profile1.jpg', 1",
            "'User comment on your post!!!!', 'profile2.png', 2"
    })
    void SendNotificationCommentToCheckTheMatchingTest(String message, String image, long notifiedUserId) {
        notification.sendNotificationComment(message, image, notifiedUserId);
        ArgumentCaptor<String> destinationCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MessageNotification> payloadCapture = ArgumentCaptor.forClass(MessageNotification.class);
        verify(messagingTemplate, times(1))
                .convertAndSend(destinationCapture.capture(), payloadCapture.capture());

        assertEquals("/topic/comment/" + notifiedUserId, destinationCapture.getValue());
        MessageNotification capturedNotification = payloadCapture.getValue();
        assertEquals(message, capturedNotification.getMessage());
        assertEquals(image, capturedNotification.getImage());
    }
    @Test
    void setNotificationRepostInNormalBehaviouralTest() {
        int expected = 1;
        notification.sendNotificationRepost("message", "image", 1);
        assertEquals( expected, 1);
    }
    @ParameterizedTest
    @CsvSource({
            "'User repost your post!', 'profile1.jpg', 1",
            "'User repost your post!!!!', 'profile2.png', 2"
    })
    void SendNotificationRepostToCheckTheMatchingTest(String message, String image, long notifiedUserId) {
        notification.sendNotificationRepost(message, image, notifiedUserId);
        ArgumentCaptor<String> destinationCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MessageNotification> payloadCapture = ArgumentCaptor.forClass(MessageNotification.class);
        verify(messagingTemplate, times(1))
                .convertAndSend(destinationCapture.capture(), payloadCapture.capture());

        assertEquals("/topic/repost/" + notifiedUserId, destinationCapture.getValue());
        MessageNotification capturedNotification = payloadCapture.getValue();
        assertEquals(message, capturedNotification.getMessage());
        assertEquals(image, capturedNotification.getImage());
    }
    @Test
    void setNotificationFollowInNormalBehaviouralTest() {
        int expected = 1;
        notification.sendNotificationFollow("message", "image", 1);
        assertEquals( expected, 1);
    }
    @ParameterizedTest
    @CsvSource({
            "'User follow you!', 'profile1.jpg', 1",
            "'User follow you!!!', 'profile2.png', 2"
    })
    void SendNotificationFollowToCheckTheMatchingTest(String message, String image, long notifiedUserId) {
        notification.sendNotificationFollow(message, image, notifiedUserId);
        ArgumentCaptor<String> destinationCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MessageNotification> payloadCapture = ArgumentCaptor.forClass(MessageNotification.class);
        verify(messagingTemplate, times(1))
                .convertAndSend(destinationCapture.capture(), payloadCapture.capture());

        assertEquals("/topic/follow/" + notifiedUserId, destinationCapture.getValue());
        MessageNotification capturedNotification = payloadCapture.getValue();
        assertEquals(message, capturedNotification.getMessage());
        assertEquals(image, capturedNotification.getImage());
    }
    @Test
    void setNotificationReportInNormalBehaviouralTest() {
        int expected = 1;
        notification.sendNotificationReport("message", "image");
        assertEquals( expected, 1);
    }
    @ParameterizedTest
    @CsvSource({
            "'User1 report user2!', 'profile1.jpg'",
            "'User2 follow user1!', 'profile2.png'"
    })
    void SendNotificationReportToCheckTheMatchingTest(String message, String image) {
        notification.sendNotificationReport(message, image);
        ArgumentCaptor<String> destinationCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MessageNotification> payloadCapture = ArgumentCaptor.forClass(MessageNotification.class);
        verify(messagingTemplate, times(1))
                .convertAndSend(destinationCapture.capture(), payloadCapture.capture());

        assertEquals("/topic/report" , destinationCapture.getValue());
        MessageNotification capturedNotification = payloadCapture.getValue();
        assertEquals(message, capturedNotification.getMessage());
        assertEquals(image, capturedNotification.getImage());
    }



    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }
}
