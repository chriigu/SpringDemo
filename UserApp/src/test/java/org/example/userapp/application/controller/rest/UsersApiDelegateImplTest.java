package org.example.userapp.application.controller.rest;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.repository.UserRepository;
import org.example.userapp.application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsersApiDelegateImplTest {


    @Test
    void getUserByUuidTestNull() {
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUserByUuid() {
    }

    @Test
    void findUsers() {
    }
}