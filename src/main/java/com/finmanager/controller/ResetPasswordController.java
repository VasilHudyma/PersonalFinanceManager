package com.finmanager.controller;

import com.finmanager.service.IResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {
    IResetPasswordService resetPasswordService;

    @Autowired
    public ResetPasswordController(IResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping("/reset")
    public boolean resetPassword(@RequestParam String email) {
        return resetPasswordService.resetPassword(email);
    }
}
