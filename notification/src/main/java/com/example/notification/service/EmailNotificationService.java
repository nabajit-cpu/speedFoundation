package com.example.notification.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.notification.model.NotificationInterface;
import com.example.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationInterface {

    private final NotificationRepository notificationRepository;

    // Email recipients
    private final List<String> emailRecipients = Arrays.asList(
        "nabajitboro777@gmail.com",
        "kidylearn3@gmail.com"
    );

    // Inject the JavaMailSender bean
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String getType() {
        return "email";
    }

    @Override
    public Boolean sendNotification(String message) {
        try {
            for (String recipient : emailRecipients) {
                // Prepare the email message
                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(recipient);
                email.setSubject("Notification Service");
                email.setText(message);
                email.setFrom("your-email@example.com"); // Replace with your verified sender email

                // Send the email
                mailSender.send(email);
                System.out.println("Email sent to: " + recipient);
            }
            return true; // All emails sent successfully
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return false; // Return false if any error occurs
        }
    }
}
