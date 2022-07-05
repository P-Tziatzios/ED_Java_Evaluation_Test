package com.dterz.controllers;

import com.dterz.dtos.AccountDTO;
import com.dterz.service.AccountService;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/account")
@CrossOrigin
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Gets an Acount by its id
     *
     * @param accountId the id of the Account to get
     * @return AccountDTO
     */
    @GetMapping("{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("accountId") long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    /**
     * Creates and returns a new empty Account Object
     *
     * @return AccountDTO
     */
    @GetMapping("_draft")
    public ResponseEntity<AccountDTO> createAccount() {
        return ResponseEntity.ok(accountService.draftAccount());
    }

    /**
     * Gets all Accounts currently in the System.
     * The parameter is optional and if provided the accounts get filtered by User
     *
     * @param userID the username
     * @return ResponseEntity<Map < String, Object>>
     */
    @GetMapping
    public ResponseEntity<?> getByUsername(@RequestParam(name = "userID", defaultValue = "") String userID) {
        PageRequest pageRequest = PageRequest.of(0, 100);
        return ResponseEntity.ok(accountService.getAll(pageRequest, userID));
    }

    /**
     * Updates the Account with the data from the Front end
     *
     * @param accountDTO the updated Account
     * @return AccountDTO
     */
    @PutMapping
    public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateAccount(accountDTO));
    }
}
