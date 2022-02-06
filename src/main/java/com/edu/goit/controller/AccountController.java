package com.edu.goit.controller;

import com.edu.goit.dto.UpdateAccountDTO;
import com.edu.goit.service.interfaces.AccountService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/user")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping(value = "/profile")
    public void updateProfile(@RequestBody UpdateAccountDTO accountDTO, Principal principal) {
        accountService.updateProfile(accountDTO, principal.getName());
    }

    @PutMapping(value = "/profile/changePassword")
    public void changePassword(@RequestParam String oldPassword,
                                                 @RequestParam String newPassword,
                                                 Principal principal) {
        accountService.changePassword(oldPassword, newPassword, principal.getName());
    }

    @GetMapping("/profile")
    public UpdateAccountDTO getUserInfo(Principal principal){
        return accountService.getUserInfoByEmail(principal.getName());
    }
}
