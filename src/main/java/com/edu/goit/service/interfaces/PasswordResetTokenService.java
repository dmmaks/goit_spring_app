package com.edu.goit.service.interfaces;

import com.edu.goit.model.PasswordResetToken;
import com.edu.goit.model.payload.AuthRequestResetUpdatePassword;

public interface PasswordResetTokenService {
    void createToken(String email);
    PasswordResetToken generateToken(Long userId);
    boolean validateResetToken(String token);
    void changePassword(AuthRequestResetUpdatePassword authRequestResetUpdatePassword);
}
