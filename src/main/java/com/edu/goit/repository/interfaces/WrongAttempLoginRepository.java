package com.edu.goit.repository.interfaces;

import com.edu.goit.model.WrongAttemptLogin;

import java.time.LocalDateTime;

public interface WrongAttempLoginRepository extends BaseCrudRepository<WrongAttemptLogin,Long>{
    WrongAttemptLogin findByIpAndTime(String ip, LocalDateTime time);
}
