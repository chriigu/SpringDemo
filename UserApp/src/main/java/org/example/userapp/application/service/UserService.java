package org.example.userapp.application.service;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.exceptions.UserAppNotFoundException;
import org.example.userapp.application.mapper.internal.UserMapper;
import org.example.userapp.application.record.*;
import org.example.userapp.application.repository.UserRepository;
import org.example.userapp.application.specifications.UserSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new UserAppNotFoundException("User not found with uuid: [" + uuid + "]");
        }

        // No need to go any further when there is nothing to update
        if(requestDto == null) {
            return userMapper.toRecord(userEntity);
        }

        userEntity.setFirstName(requestDto.firstName());
        userEntity.setLastName(requestDto.lastName());
        userEntity.setEmail(requestDto.email());
        // Birthdate cannot be changed. Maybe an administrator thing for the future but not in general

        return userMapper.toRecord(userRepository.saveAndFlush(userEntity));
    }

    public UserSearchResultRecord searchUsers(final UserSearchQueryParamsRecord userSearchQueryParamsDto) {
        Page<UserEntity> userEntities = userRepository.findAll(UserSpecifications.withFilters(
                userSearchQueryParamsDto.firstName(),
                userSearchQueryParamsDto.lastName(),
                userSearchQueryParamsDto.email(),
                userSearchQueryParamsDto.birthdate()
        ), PageRequest.of(
                0,
                userSearchQueryParamsDto.limit(),
                Sort.by(Sort.Direction.fromString(userSearchQueryParamsDto.orderDirection().toString()), userSearchQueryParamsDto.orderBy().toString())
        ));
        log.info("UserEntity Result Count [{}]", userEntities.getNumberOfElements());
        return new UserSearchResultRecord(userEntities.stream().map(userMapper::toRecord).toList(),
                userEntities.getTotalElements(),
                userEntities.getNumber(),
                userEntities.getSize(),
                userEntities.getTotalPages());
    }

    @Transactional
    public void deleteUserByUuid(final String uuid) {
        userRepository.deleteByUuidEquals(uuid);
    }
}
