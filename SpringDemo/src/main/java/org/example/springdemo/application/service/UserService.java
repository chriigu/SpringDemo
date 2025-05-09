package org.example.springdemo.application.service;

import org.example.springdemo.application.dto.CreateUserRequestDto;
import org.example.springdemo.application.dto.UpdateUserRequestDto;
import org.example.springdemo.application.entity.UserEntity;
import org.example.springdemo.application.enums.OrderDirectionEnum;
import org.example.springdemo.application.enums.UserSearchOrderByEnum;
import org.example.springdemo.application.mapper.internal.UserMapper;
import org.example.springdemo.application.record.UserRecord;
import org.example.springdemo.application.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserRecord findUserByUuid(String uuid) {
        UserEntity byUuid = userRepository.findByUuidEquals(uuid);
        log.info("UserEntityByUuid Result [{}]", byUuid);
        return userMapper.toRecord(byUuid);
    }

    @Transactional
    public UserRecord createUser(CreateUserRequestDto requestDto) {
        UserEntity userEntity = new UserEntity();
        userRepository.save(userEntity);
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(requestDto.getFirstName());
        userEntity.setLastName(requestDto.getLastName());
        userEntity.setEmail(requestDto.getEmail());
        userEntity.setBirthdate(requestDto.getBirthDate());

        return userMapper.toRecord(userRepository.saveAndFlush(userEntity));
    }

    @Transactional
    public UserRecord updateUser(String uuid, UpdateUserRequestDto requestDto) {
        UserEntity userEntity = userRepository.findByUuidEquals(uuid);

        if(userEntity == null) {
            throw new RuntimeException("User not found");
        }

        userEntity.setFirstName(requestDto.getFirstName());
        userEntity.setLastName(requestDto.getLastName());
        userEntity.setEmail(requestDto.getEmail());

        return userMapper.toRecord(userRepository.saveAndFlush(userEntity));
    }

    public Collection<UserRecord> searchUsers(String firstName, String lastName, String email, LocalDate birthdate, UserSearchOrderByEnum order, OrderDirectionEnum orderDirection, int page, int limit) {
        Collection<UserEntity> userEntities = userRepository.findUserEntities(firstName, lastName, email, birthdate, order, orderDirection, page, limit);
        log.info("UserEntity Result Count [{}]", userEntities.size());
        return userEntities.stream().map(userMapper::toRecord).toList();
    }

    @Transactional
    public void deleteUserByUuid(String uuid) {
        userRepository.deleteByUuidEquals(uuid);
    }
}
