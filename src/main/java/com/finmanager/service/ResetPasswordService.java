package com.finmanager.service;

import com.finmanager.dao.IUserDAO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService implements IResetPasswordService {

    private IUserDAO userDAO;

    private final JavaMailSender mailSender;

    @Autowired
    public ResetPasswordService(IUserDAO userDAO, JavaMailSender mailSender) {
        this.userDAO = userDAO;
        this.mailSender = mailSender;
    }

    @Override
    public boolean resetPassword(String email) {
        String newPassword = RandomStringUtils.random(6, true, true);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("PersonalFinanceManager");
        mailMessage.setTo(email);
        mailMessage.setSubject("New Password");
        mailMessage.setText("Hello! It's your new Password: \n" + newPassword);
        userDAO.updatePassword(email, newPassword);
        return true;
    }
}
