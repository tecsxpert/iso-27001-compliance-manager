package com.internship.tool.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String body) {
        System.out.println("📧 Email skipped (not configured yet)");
    }
}