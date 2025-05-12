package org.example.userapp.application.service;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.exceptions.UserAppNotFoundException;
import org.example.userapp.application.mapper.internal.UserMapper;
import org.example.userapp.application.record.CreateUserRequestRecord;
import org.example.userapp.application.record.UpdateUserRequestRecord;
import org.example.userapp.application.record.UserRecord;
import org.example.userapp.application.record.UserSearchQueryParamsRecord;
import org.example.userapp.application.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserRecord findUserByUuid(final String uuid) {
        UserEntity byUuid = userRepository.findByUuidEquals(uuid);
        log.info("UserEntityByUuid Result [{}]", byUuid);
        return userMapper.toRecord(byUuid);
    }

    @Transactional
    public UserRecord createUser(final CreateUserRequestRecord requestDto) {
        UserEntity userEntity = new UserEntity();
        userRepository.save(userEntity);
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(requestDto.firstName());
        userEntity.setLastName(requestDto.lastName());
        userEntity.setEmail(requestDto.email());
        userEntity.setBirthdate(requestDto.birthDate());

        return userMapper.toRecord(userRepository.saveAndFlush(userEntity));
    }

    @Transactional
    public UserRecord updateUser(final String uuid, final UpdateUserRequestRecord requestDto) {
        UserEntity userEntity = userRepository.findByUuidEquals(uuid);

        if(userEntity == null) {
            throw new UserAppNotFoundException("User not found");
        }

        userEntity.setFirstName(requestDto.firstName());
        userEntity.setLastName(requestDto.lastName());
        userEntity.setEmail(requestDto.email());

        return userMapper.toRecord(userRepository.saveAndFlush(userEntity));
    }

    public Collection<UserRecord> searchUsers(final UserSearchQueryParamsRecord userSearchQueryParamsDto) {
        Collection<UserEntity> userEntities = userRepository.findUserEntities(userSearchQueryParamsDto);
        log.info("UserEntity Result Count [{}]", userEntities.size());
        return userEntities.stream().map(userMapper::toRecord).toList();
    }

    @Transactional
    public void deleteUserByUuid(final String uuid) {
        userRepository.deleteByUuidEquals(uuid);
    }
}
