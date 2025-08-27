package com.civictracker.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(String toEmail, String userName, String issueId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@civictracker.com");
            message.setTo(toEmail);
            message.setSubject("Issue Report Confirmation - #" + issueId.substring(0, 8));
            message.setText("Dear " + userName + ",\n\nThank you for your submission. We have received your issue report and will process it shortly.\n\nYour issue ID is: " + issueId + "\n\nSincerely,\nThe Civic Tracker Team");
            
            mailSender.send(message);
            log.info("Successfully sent confirmation email for issue {} to {}", issueId, toEmail);
        } catch (Exception e) {
            log.error("Failed to send email for issue {}: {}", issueId, e.getMessage());
        }
    }
}