package org.example.userapp.application.mapper.api;

import java.util.Objects;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;
import org.example.userapp.application.openapi.model.*;
import org.example.userapp.application.record.CreateUserRequestRecord;
import org.example.userapp.application.record.UpdateUserRequestRecord;
import org.example.userapp.application.record.UserRecord;
import org.example.userapp.application.record.UserSearchResultRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class APIMapperTest {

    private final APIMapper apiMapper = new APIMapper();

    @Test
    void mapUserRecordToOAUserDto() {
        // given
        UserRecord input = new UserRecord(UUID.randomUUID(), "em", "fn", "ln", LocalDate.of(1990, 1, 1));
        // when
        OAUserDto result = apiMapper.mapUserRecordToOAUserDto(input);

        // then
        assertNotNull(result);
        assertNotNull(result.getUuid());
        assertEquals("fn", result.getFirstName());
        assertEquals("ln", result.getLastName());
        assertEquals("em", result.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthdate());
    }

    @Test
    void mapUserSearchResultRecordToOAUserSearchResult() {
        // given
        UUID uuid1 = UUID.randomUUID();
        UserRecord user1 = new UserRecord(
                uuid1, "em1", "fn1", "ln1", LocalDate.of(1990, 1, 1)
        );

        UUID uuid2 = UUID.randomUUID();
        UserRecord user2 = new UserRecord(
                uuid2, "em2", "fn2", "ln2", LocalDate.of(1991, 1, 1)
        );

        List<UserRecord> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        UserSearchResultRecord input = new UserSearchResultRecord(
                users, 2, 1, 1, 2

        );
        // when
        OAUserSearchResult result = apiMapper.mapUserSearchResultRecordToOAUserSearchResult(input);

        // then
        assertNotNull(result);
        assertEquals(2, result.getResultList().size());
        assertEquals(2, result.getTotalResults());
        assertEquals(2, result.getTotalPages());
        assertEquals(1, result.getPageNumber());
        assertEquals(1, result.getPageSize());

        // assert users
        assertNotNull(result.getResultList().getFirst());
        assertEquals(uuid1, result.getResultList().getFirst().getUuid());
        assertEquals("fn1", result.getResultList().getFirst().getFirstName());
        assertEquals("ln1", result.getResultList().getFirst().getLastName());
        assertEquals("em1", result.getResultList().getFirst().getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), result.getResultList().getFirst().getBirthdate());

        assertNotNull(result.getResultList().getLast());
        assertEquals(uuid2, result.getResultList().getLast().getUuid());
        assertEquals("fn2", result.getResultList().getLast().getFirstName());
        assertEquals("ln2", result.getResultList().getLast().getLastName());
        assertEquals("em2", result.getResultList().getLast().getEmail());
        assertEquals(LocalDate.of(1991, 1, 1), result.getResultList().getLast().getBirthdate());
    }

    private static Stream<Arguments> orderSearchEnumArgs() {
        return Stream.of(
                Arguments.of(OAUserSearchOrderEnum.FIRST_NAME, UserSearchOrderByEnum.FIRST_NAME),
                Arguments.of(OAUserSearchOrderEnum.LAST_NAME, UserSearchOrderByEnum.LAST_NAME),
                Arguments.of(OAUserSearchOrderEnum.EMAIL, UserSearchOrderByEnum.EMAIL),
                Arguments.of(OAUserSearchOrderEnum.BIRTHDATE, UserSearchOrderByEnum.BIRTHDATE)
        );
    }

    @ParameterizedTest
    @MethodSource("orderSearchEnumArgs")
    void mapOAUserSearchOrderEnumToUserSearchOrderByEnum(OAUserSearchOrderEnum input, UserSearchOrderByEnum expected) {
        // given
        // when
        UserSearchOrderByEnum result = apiMapper.mapOAUserSearchOrderEnumToUserSearchOrderByEnum(input);

        // then
        assertNotNull(result);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> orderDirectionEnumArgs() {
        return Stream.of(
                Arguments.of(OAOrderDirectionEnum.ASC, OrderDirectionEnum.ASC),
                Arguments.of(OAOrderDirectionEnum.DESC, OrderDirectionEnum.DESC)
        );
    }

    @ParameterizedTest
    @MethodSource("orderDirectionEnumArgs")
    void mapOAOrderDirectionEnumToOrderDirectionEnum(OAOrderDirectionEnum input, OrderDirectionEnum expected) {
        // given
        // when
        OrderDirectionEnum result = apiMapper.mapOAOrderDirectionEnumToOrderDirectionEnum(input);

        // then
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void mapOACreateUserRequestToCreateUserRequestRecord() {
        // given
        OACreateUserRequest input = new OACreateUserRequest();
        input.setEmail("em");
        input.setFirstName("fn");
        input.setLastName("ln");
        input.setBirthdate(LocalDate.of(1990, 1, 1));

        // when
        CreateUserRequestRecord result = apiMapper.mapOACreateUserRequestToCreateUserRequestRecord(input);

        // then
        assertNotNull(result);
        assertEquals("fn", result.firstName());
        assertEquals("ln", result.lastName());
        assertEquals("em", result.email());
        assertEquals(LocalDate.of(1990, 1, 1), result.birthDate());
    }

    @Test
    void mapOAUpdateUserRequestToUpdateUserRequestRecord() {
        // given
        OAUpdateUserRequest input = new OAUpdateUserRequest();
        input.setEmail("em2");
        input.setFirstName("fn2");
        input.setLastName("ln2");

        // when
        UpdateUserRequestRecord result = apiMapper.mapOAUpdateUserRequestToUpdateUserRequestRecord(input);

        // then
        assertNotNull(result);
        assertEquals("fn2", result.firstName());
        assertEquals("ln2", result.lastName());
        assertEquals("em2", result.email());
    }
}