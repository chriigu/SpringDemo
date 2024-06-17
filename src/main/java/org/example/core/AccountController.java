package org.example.core;

import org.example.record.UserRecord;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserRecord user(@RequestParam(value = "userId", defaultValue = "") String userId) {
        return userService.findUserByUuid(userId);
    }

}
