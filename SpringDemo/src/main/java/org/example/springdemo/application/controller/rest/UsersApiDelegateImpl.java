package org.example.springdemo.application.controller.rest;

import org.example.springdemo.application.mapper.api.APIMapper;
import org.example.springdemo.application.openapi.api.UsersApiDelegate;
import org.example.springdemo.application.openapi.model.OACreateUserRequest;
import org.example.springdemo.application.openapi.model.OAOrderDirectionEnum;
import org.example.springdemo.application.openapi.model.OAUserDto;
import org.example.springdemo.application.openapi.model.OAUserSearchOrderEnum;
import org.example.springdemo.application.service.UserService;
import org.example.springdemo.application.validators.ParamValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private static final Logger log = LoggerFactory.getLogger(UsersApiDelegateImpl.class);


    private final UserService userService;
    private final APIMapper apiMapper;

    @Autowired
    public UsersApiDelegateImpl(UserService userService, APIMapper apiMapper) {
        this.userService = userService;
        this.apiMapper = apiMapper;
    }

    @Override
    public ResponseEntity<OAUserDto> getUserByUuid(UUID uuid) {
        log.info("Get user with uuid [{}]", uuid);

        OAUserDto userByUuid = apiMapper.mapUserToAPIUserDtoResponseEntity(
                userService.findUserByUuid(uuid.toString()));

        if (userByUuid != null) {
            return ResponseEntity.ok(userByUuid);
        }

        return ResponseEntity.ofNullable(null);
    }

    @Override
    public ResponseEntity<OAUserDto> createUser(OACreateUserRequest createUserRequest) {
        return UsersApiDelegate.super.createUser(createUserRequest);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID uuid) {
        return UsersApiDelegate.super.deleteUser(uuid);
    }

    @Override
    public ResponseEntity<OAUserDto> updateUserByUuid(UUID uuid, OAUserDto userDto) {
        return UsersApiDelegate.super.updateUserByUuid(uuid, userDto);
    }

    @Override
    public ResponseEntity<List<OAUserDto>> findUsers(OAOrderDirectionEnum orderDirection,
                                                   OAUserSearchOrderEnum order,
                                                   Integer page,
                                                   Integer limit,
                                                   String firstName,
                                                   String lastName,
                                                   String email,
                                                   LocalDate birthdate) {
        log.info("Get users");
        if (ParamValidator.isNotNullAndNotBlank(firstName) ||
                ParamValidator.isNotNullAndNotBlank(lastName) ||
                ParamValidator.isNotNullAndNotBlank(email) ||
                birthdate != null) {
            return ResponseEntity.ok(
                    userService.searchUsers(
                            firstName,
                            lastName,
                            email,
                            birthdate,
                            apiMapper.mapUserSearchOrderEnum(order),
                            apiMapper.mapOrderDirectionEnum(orderDirection),
                            page,
                            limit)
                    .stream()
                    .map(apiMapper::mapUserToAPIUserDtoResponseEntity)
                    .toList());
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
