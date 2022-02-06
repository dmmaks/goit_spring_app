package com.edu.goit.service.interfaces;

import com.edu.goit.model.WrongAttemptLogin;

import java.time.LocalDateTime;

public interface WrongAttemptLoginService {
    WrongAttemptLogin findSessionByIpAndTime(String ip, LocalDateTime time);
    void createSession(WrongAttemptLogin wrongAttemptLogin);
    void updateSession(WrongAttemptLogin wrongAttemptLogin);
}
