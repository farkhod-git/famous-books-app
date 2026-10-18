package com.farkhod.famousbooksapp.service;

public interface MailService {
    void sendConfirmation(String to, String confirmation);
}
