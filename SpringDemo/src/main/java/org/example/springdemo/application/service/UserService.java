package org.example.springdemo.application.service;

import org.example.springdemo.application.entity.UserEntity;
import org.example.springdemo.application.enums.OrderDirectionEnum;
import org.example.springdemo.application.enums.UserSearchOrderByEnum;
import org.example.springdemo.application.mapper.internal.UserMapper;
import org.example.springdemo.application.record.UserRecord;
import org.example.springdemo.application.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRecord findUserByUuid(String uuid) {
        UserEntity byUuid = userRepository.findByUuidEquals(uuid);
        log.info("UserEntityByUuid Result [{}]", byUuid);
        return UserMapper.toRecord(byUuid);
    }

    public Collection<UserRecord> findUsers(String firstName, String lastName, String email, LocalDate birthdate) {
        Collection<UserEntity> userEntities = userRepository.findAll();
        log.info("UserEntity Result Count [{}]", userEntities.size());
        return userEntities.stream().map(UserMapper::toRecord).toList();
    }

    public Collection<UserRecord> searchUsers(String firstName, String lastName, String email, LocalDate birthdate, UserSearchOrderByEnum order, OrderDirectionEnum orderDirection, int page, int limit) {
        Collection<UserEntity> userEntities = userRepository.findUserEntities(firstName, lastName, email, birthdate, order, orderDirection, page, limit);
        log.info("UserEntity Result Count [{}]", userEntities.size());
        return userEntities.stream().map(UserMapper::toRecord).toList();
    }
}
