package com.dterz.controllers;

import com.dterz.dtos.UserDTO;
import com.dterz.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/user")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Gets all the available Users currently in the System
     *
     * @return List<UserDTO>
     */
    @GetMapping
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Gets a User by its id
     *
     * @param userId the id of the User requested
     * @return UserDTO
     */
    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") long userId) {
        log.debug("Called: getuser with {}", userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Updates the User with the data from the Front end
     *
     * @param user the updated User
     * @return UserDTO
     */
    @PostMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * Creates and returns a new empty User Object
     *
     * @return UserDTO
     */
    @GetMapping("_draft")
    public ResponseEntity<UserDTO> createDraftUser() {
        return ResponseEntity.ok(userService.createDraftUser());
    }

    /**
     * Deletes an User from the Database
     *
     * @param userId the id of the User to delete
     */
    @DeleteMapping("{userId}")
    public void delete(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
    }
}
