package org.example.springdemo.application.controller.rest;

import org.example.openapi.api.UserApiDelegate;
import org.example.springdemo.application.record.UserRecord;
import org.example.springdemo.application.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController implements UserApiDelegate {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserRecord readUser(@RequestParam(value = "userId", defaultValue = "") String userId) {
        log.info("Get user with {}", userId);
        return userService.findUserByUuid(userId);
    }

    @GetMapping("/users")
    public Collection<UserRecord> readUsers(@RequestParam(value = "search", defaultValue = "") String searchParam) {
        log.info("Get users");
        if(searchParam == null || searchParam.isEmpty()) {
            return userService.findUsers();
        }
        return userService.searchUsers(searchParam);
    }
}
