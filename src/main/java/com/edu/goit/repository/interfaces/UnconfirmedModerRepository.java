package com.edu.goit.repository.interfaces;

import com.edu.goit.model.UnconfirmedModerator;

import java.time.LocalDateTime;

public interface UnconfirmedModerRepository extends BaseCrudRepository<UnconfirmedModerator, Integer> {
    UnconfirmedModerator getByToken(String token);
    LocalDateTime findLatestExpiryDate(String email);
}
