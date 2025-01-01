package com.example.backend.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class SendEmailService {
    @Value("${sendgrid.api.key}")
    private String key;

    @Value("${email.sender}")
    private String emailSender;

    public static final String RESET_PASSWORD_TITLE = "Reset Your Password";
    public static final String RESET_PASSWORD_MAIL_TEMPLATE = "To reset your account Password, \nUse the following code: %s";
    public static final String EMAIL_VERIFICATION_TITLE = "Verify Your Account";
    public static final String EMAIL_VERIFICATION_MAIL_TEMPLATE = "To verify your account, \nUse the following code: %s";

    public void sendResetPasswordOTPEmail(String toEmail, String otpCode) throws IOException {
        String message = String.format(RESET_PASSWORD_MAIL_TEMPLATE, otpCode);
        Email from = new Email(emailSender);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, RESET_PASSWORD_TITLE, to, content);
        sendEmail(mail);
    }

    public void sendEmailVerification(String toEmail, String otpCode) throws IOException {
        String message = String.format(EMAIL_VERIFICATION_MAIL_TEMPLATE, otpCode);
        Email from = new Email(emailSender);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, EMAIL_VERIFICATION_TITLE, to, content);
        sendEmail(mail);
    }

    public void sendEmail(Mail mail) throws IOException {
        SendGrid sg = new SendGrid(key);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
