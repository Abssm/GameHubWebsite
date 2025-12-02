package com.gamehub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    /**
     * Send email
     */
    public void sendEmail(String to, String subject, String body, boolean isHtml) {
        try {
            log.info("Sending email to: {} with subject: {}", to, subject);
            // TODO: Implement actual email sending using JavaMailSender or external service
            // For now, just log the email
            log.debug("Email body: {}", body);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
        }
    }
    
    /**
     * Send simple text email
     */
    public void sendSimpleEmail(String to, String subject, String body) {
        sendEmail(to, subject, body, false);
    }
}
