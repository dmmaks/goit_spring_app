package com.edu.goit.service;

import com.edu.goit.dto.AccountDTO;
import com.edu.goit.dto.AccountPersonalInfoDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.dto.UpdateAccountDTO;
import com.edu.goit.model.AccountRole;
import com.edu.goit.model.Credentials;
import com.edu.goit.model.WrongAttemptLogin;
import com.edu.goit.exception.CustomException;
import com.edu.goit.exception.FailedAuthorizationException;
import com.edu.goit.mapper.AccountMapper;
import com.edu.goit.mapper.CredentialsMapper;
import com.edu.goit.model.Account;
import com.edu.goit.repository.interfaces.AccountRepository;
import com.edu.goit.repository.interfaces.CredentialsRepository;
import com.edu.goit.security.JwtTokenProvider;
import com.edu.goit.service.interfaces.AccountService;
import com.edu.goit.service.interfaces.CaptchaService;
import com.edu.goit.service.interfaces.WrongAttemptLoginService;
import com.edu.goit.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CredentialsRepository credentialsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final AccountMapper accountMapper;
    private final WrongAttemptLoginService wrongAttemptLoginService;
    private final CaptchaService captchaService;

    @Override
    public String signIn(String username, String password, String recaptcha_token, String ip) {
        boolean needCaptcha = false;
        WrongAttemptLogin sessionUserWrongAttempt = wrongAttemptLoginService.findSessionByIpAndTime(ip, LocalDateTime.now());
        try {
            if(sessionUserWrongAttempt != null && sessionUserWrongAttempt.getCountWrongAttempts() >= 5) {
                needCaptcha = true;
                if(recaptcha_token == null || recaptcha_token.isEmpty())
                    throw new FailedAuthorizationException(HttpStatus.UNPROCESSABLE_ENTITY, "Need captcha", needCaptcha);
                if(!captchaService.isValidCaptcha(recaptcha_token)) {
                    wrongAttemptLoginService.updateSession(sessionUserWrongAttempt);
                    throw new FailedAuthorizationException(HttpStatus.UNPROCESSABLE_ENTITY, "Recaptcha token is invalid", needCaptcha);
                }
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, accountRepository.findByEmail(username).getAccountRole());
        } catch (AuthenticationException e) {
            if(sessionUserWrongAttempt == null) {
                wrongAttemptLoginService.createSession(new WrongAttemptLogin(ip, LocalDateTime.now(), 1));
            }
            else {
                wrongAttemptLoginService.updateSession(sessionUserWrongAttempt);
            }
            throw new FailedAuthorizationException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied", needCaptcha);
        }
    }

    @Override
    @Transactional
    public void signUp(AccountDTO accountDTO) {
        try {
            createNewAccount(accountDTO, AccountRole.ROLE_USER);
        } catch (DuplicateKeyException ex) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Email already exists");
        }
    }

    private void createNewAccount(AccountDTO accountDTO, AccountRole accountRole){
        long uniqueId = Utils.generateUniqueId();

        Credentials credentials = credentialsMapper.accountDTOtoCredentials(accountDTO);
        Account account = accountMapper.accountDTOtoAccount(accountDTO);

        credentials.setId(uniqueId);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        account.setId(uniqueId);
        account.setAccountRole(accountRole);

        credentialsRepository.create(credentials);
        accountRepository.create(account);
    }

    @Override
    public void updateProfile(UpdateAccountDTO accountDTO, String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        Account accountUpdate = accountMapper.updateAccountDTOtoAccount(accountDTO);
        accountUpdate.setId(credentials.getId());
        accountRepository.update(accountUpdate);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        String requiredPassword = credentials.getPassword();
        if (!passwordEncoder.matches(oldPassword, requiredPassword)) {
            throw new CustomException(HttpStatus.CONFLICT, "Entered invalid old password");
        }
        credentials.setPassword(passwordEncoder.encode(newPassword));
        credentialsRepository.update(credentials);
    }

    @Override
    public void updatePersonalInfo(AccountPersonalInfoDTO accountDto) {
        Account account = accountMapper.accountPersonalInfoDTOtoAccounts(accountDto);
        try {
            accountRepository.update(account);
        } catch (DataAccessException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no account with such id");
        }
    }

    @Override
    public void updateUserStatus(long id) {
        try {
            accountRepository.updateStatus(id);
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, "There is no account with such id");
        }
    }

    @Override
    public UpdateAccountDTO getUserInfoByEmail(String email) {
        try {
            Account account = accountRepository.findByEmail(email);
            return accountMapper.accountToUpdateAccountDTO(account);
        }catch (EmptyResultDataAccessException ex){throw new CustomException(HttpStatus.NOT_FOUND, "Account not found");}
    }

    @Override
    public PaginationDTO<AccountPersonalInfoDTO> getAllUsersBySearch(String search, int currentPage, int limit,
                                                                     boolean order, String gender, String status) {
        int totalElements = accountRepository.countAccountsBySearch(search, AccountRole.ROLE_USER, gender, status);
        Collection<Account> accounts = accountRepository.findAccountsBySearch(
                search, gender, AccountRole.ROLE_USER, status, limit,  currentPage * limit, order
        );
        return new PaginationDTO<>(accountMapper.accountsToPersonalInfoDtoCollection(accounts),  totalElements);
    }


    @Override
    public AccountPersonalInfoDTO findById (long id) {
        Account account = accountRepository.findById(id);
        Credentials credentials = credentialsRepository.findById(id);

        if (account == null || credentials == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "no accounts found with such id");
        }
        AccountPersonalInfoDTO accountDto = accountMapper.accountToAccountPersonalInfoDto(account);
        accountDto.setEmail(credentials.getEmail());
        return accountDto;
    }
}
