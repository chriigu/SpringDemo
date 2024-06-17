package org.example.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountManagementApplication {

    private static final Logger log = LoggerFactory.getLogger(AccountManagementApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(AccountManagementApplication.class);
    }
}
