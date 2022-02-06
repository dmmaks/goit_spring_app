package com.edu.goit.repository.interfaces;

import com.edu.goit.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends BaseCrudRepository<PasswordResetToken, Long>{
    PasswordResetToken findByAccountId(Long id);
    void disableAllTokensByAccountId(Long id);
    PasswordResetToken findByToken(String token);
}
