package com.edu.goit.controller;

import com.edu.goit.dto.AccountPersonalInfoDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.dto.UpdateAccountDTO;
import com.edu.goit.service.interfaces.AccountService;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public PaginationDTO<AccountPersonalInfoDTO> getAllBySearch(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
            @RequestParam(value = "gender", defaultValue = "", required = false) String gender,
            @RequestParam(value = "status", defaultValue = "", required = false) String status){
        return accountService.getAllUsersBySearch(search, currentPage, size, order, gender, status);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ApiResponse(code = 404, message = "There is no account with such id")
    public HttpStatus updateStatus(@PathVariable long id) {
        accountService.updateUserStatus(id);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    @ApiResponse(code = 404, message = "There is no account with such id")
    public void updateUser(@RequestBody AccountPersonalInfoDTO accountDTO) {
        accountService.updatePersonalInfo(accountDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @ApiResponse(code = 404, message = "There is no account with such id")
    public AccountPersonalInfoDTO getUserById(@PathVariable long id) {
        return accountService.findById(id);
    }
}
