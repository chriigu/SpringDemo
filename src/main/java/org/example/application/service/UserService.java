package org.example.application.service;

import org.example.application.entity.UserEntity;
import org.example.application.mapper.internal.UserMapper;
import org.example.application.record.UserRecord;
import org.example.application.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRecord findUserByUuid(String uuid) {
        UserEntity byUuid = userRepository.findByUuidEquals(uuid);

        // Search all users
        List<UserEntity> all = userRepository.findAll();
        log.info("UserEntity Result {}", byUuid);
        log.info("UserEntity Count Result {}", all.size());
        return UserMapper.toRecord(byUuid);
//        return null;
    }
}
