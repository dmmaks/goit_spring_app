package com.edu.goit.service.interfaces;

import com.edu.goit.dto.AccountDTO;
import com.edu.goit.dto.AccountPersonalInfoDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.dto.UpdateAccountDTO;

public interface AccountService {
    void updateProfile(UpdateAccountDTO accountDTO, String email);
    void changePassword(String oldPassword, String newPassword, String email);
    String signIn(String username, String password, String recaptcha_token, String ip);
    void signUp (AccountDTO accountDTO);
    UpdateAccountDTO getUserInfoByEmail(String email);
    AccountPersonalInfoDTO findById (long id);
    PaginationDTO<AccountPersonalInfoDTO> getAllBySearchModerators(String search, int currentPage, int limit, boolean order, String gender, String status);
    void updatePersonalInfo(AccountPersonalInfoDTO accountDto);
    void updateModerStatus(long id);
}
