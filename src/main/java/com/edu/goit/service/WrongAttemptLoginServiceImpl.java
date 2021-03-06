package com.edu.goit.service;

import com.edu.goit.repository.interfaces.WrongAttempLoginRepository;
import com.edu.goit.model.WrongAttemptLogin;
import com.edu.goit.service.interfaces.WrongAttemptLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WrongAttemptLoginServiceImpl implements WrongAttemptLoginService {

    private final WrongAttempLoginRepository wrongAttempLoginRepository;
    @Value("${expiryCaptchaForUser}")
    private Long expiryCaptchaForUserInMinutes;

    public WrongAttemptLoginServiceImpl(WrongAttempLoginRepository wrongAttempLoginRepository){
        this.wrongAttempLoginRepository = wrongAttempLoginRepository;
    }

    public WrongAttemptLogin findSessionByIpAndTime(String ip, LocalDateTime time){
        try {
            return wrongAttempLoginRepository.findByIpAndTime(ip, time);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public void createSession(WrongAttemptLogin wrongAttemptLogin){
        wrongAttemptLogin.setExpiryTime(wrongAttemptLogin.getExpiryTime().plusMinutes(expiryCaptchaForUserInMinutes));
        wrongAttempLoginRepository.create(wrongAttemptLogin);
    }

    public void updateSession(WrongAttemptLogin wrongAttemptLogin){
        wrongAttemptLogin.setExpiryTime(LocalDateTime.now().plusMinutes(expiryCaptchaForUserInMinutes));
        wrongAttemptLogin.setCountWrongAttempts(wrongAttemptLogin.getCountWrongAttempts()+1);
        wrongAttempLoginRepository.update(wrongAttemptLogin);
    }
}
