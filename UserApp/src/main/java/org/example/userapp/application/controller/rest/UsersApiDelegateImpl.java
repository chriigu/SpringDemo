package org.example.userapp.application.controller.rest;

import org.example.userapp.application.mapper.api.APIMapper;
import org.example.userapp.application.openapi.api.UsersApiDelegate;
import org.example.userapp.application.openapi.model.*;
import org.example.userapp.application.openapi.model.*;
import org.example.userapp.application.record.UserSearchQueryParamsRecord;
import org.example.userapp.application.service.UserService;
import org.example.userapp.application.validators.ParamValidator;
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
    public UsersApiDelegateImpl(final UserService userService, final APIMapper apiMapper) {
        this.userService = userService;
        this.apiMapper = apiMapper;
    }

    @Override
    public ResponseEntity<OAUserDto> getUserByUuid(final UUID uuid) {
        log.info("Get user with uuid [{}]", uuid);

        OAUserDto userByUuid = apiMapper.mapUserRecordToOAUserDto(
                userService.findUserByUuid(uuid.toString()));

        if (userByUuid != null) {
            return ResponseEntity.ok(userByUuid);
        }

        return ResponseEntity.ofNullable(null);
    }

    @Override
    public ResponseEntity<OAUserDto> createUser(final OACreateUserRequest createUserRequest) {
        log.info("Create user [{}]", createUserRequest);

        OAUserDto userByUuid = apiMapper.mapUserRecordToOAUserDto(
                userService.createUser(apiMapper.mapOACreateUserRequestToCreateUserRequestRecord(createUserRequest)));

        if (userByUuid != null) {
            return ResponseEntity.ok(userByUuid);
        }

        return ResponseEntity.ofNullable(null);
    }

    @Override
    public ResponseEntity<Void> deleteUser(final UUID uuid) {
        log.info("Delete user with uuid [{}]", uuid);

        userService.deleteUserByUuid(uuid.toString());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<OAUserDto> updateUserByUuid(final UUID uuid, final OAUpdateUserRequest request) {
        log.info("Update user with uuid [{}] and params [{}]", uuid, request);

        OAUserDto userByUuid = apiMapper.mapUserRecordToOAUserDto(
                userService.updateUser(uuid.toString(), apiMapper.mapOAUpdateUserRequestToUpdateUserRequestRecord(request)));

        if (userByUuid != null) {
            return ResponseEntity.ok(userByUuid);
        }

        return ResponseEntity.ofNullable(null);
    }

    @Override
    public ResponseEntity<List<OAUserDto>> findUsers(final OAOrderDirectionEnum orderDirection,
                                                   final OAUserSearchOrderEnum order,
                                                   final Integer page,
                                                   final Integer limit,
                                                   final String firstName,
                                                   final String lastName,
                                                   final String email,
                                                   final LocalDate birthdate) {
        log.info("Find users with params firstName [{}], lastName [{}], email [{}], birthdate [{}}], orderDirection [{}], order [{}], page [{}], limit [{}]",
                firstName, lastName, email, birthdate, orderDirection, order, page, limit);

        if (ParamValidator.isNotNullAndNotBlank(firstName) ||
                ParamValidator.isNotNullAndNotBlank(lastName) ||
                ParamValidator.isNotNullAndNotBlank(email) ||
                birthdate != null) {
            return ResponseEntity.ok(
                    userService.searchUsers(
                                    new UserSearchQueryParamsRecord(
                                            firstName,
                                            lastName,
                                            email,
                                            birthdate,
                                            apiMapper.mapOAUserSearchOrderEnumToUserSearchOrderByEnum(order),
                                            apiMapper.mapOAOrderDirectionEnumToOrderDirectionEnum(orderDirection),
                                            page,
                                            limit))
                    .stream()
                    .map(apiMapper::mapUserRecordToOAUserDto)
                    .toList());
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
