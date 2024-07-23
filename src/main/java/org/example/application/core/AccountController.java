package org.example.application.core;

import org.example.application.record.UserRecord;
import org.example.application.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);


    public final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserRecord user(@RequestParam(value = "userId", defaultValue = "") String userId) {
        log.info("Get user with {}", userId);
        return userService.findUserByUuid(userId);
    }

}
