package com.edu.goit.service.interfaces;

import com.edu.goit.dto.NewModeratorDTO;
import com.edu.goit.model.payload.AuthRequestResetUpdatePassword;

public interface ModerCreationService {
    void createToken(NewModeratorDTO moderatorDTO);
    boolean validateModerToken(String token);
    void createAccount(AuthRequestResetUpdatePassword authRequestResetUpdatePassword);
}
