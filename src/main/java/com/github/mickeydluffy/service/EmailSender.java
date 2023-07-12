package com.github.mickeydluffy.service;

public interface EmailSender {
    void sendEmail(String to, String subject, String text);
}
