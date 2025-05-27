package org.example.userapp.application.service;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;
import org.example.userapp.application.exceptions.UserAppNotFoundException;
import org.example.userapp.application.mapper.internal.UserMapper;
import org.example.userapp.application.record.CreateUserRequestRecord;
import org.example.userapp.application.record.UpdateUserRequestRecord;
import org.example.userapp.application.record.UserRecord;
import org.example.userapp.application.record.UserSearchQueryParamsRecord;
import org.example.userapp.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void getUserByUuidTestNull() {
        // given
        when(userRepository.findByUuidEquals(anyString())).thenReturn(null);

        // when
        UserAppNotFoundException result = assertThrows(UserAppNotFoundException.class, () ->
                userService.findUserByUuid("nonExistingUuid"));

        // then
        assertEquals("User not found with uuid: [nonExistingUuid]", result.getMessage());
    }

    @Test
    void getUserByUuidTest() {
        // given
        UserEntity userEntity = new UserEntity();
        when(userRepository.findByUuidEquals(eq("uuid"))).thenReturn(userEntity);
        UserRecord userRecord = new UserRecord(UUID.randomUUID(), "firstName", "lastName", "email", LocalDate.of(1990,1,1));
        when(userMapper.toRecord(eq(userEntity))).thenReturn(userRecord);

        // when
        UserRecord result = userService.findUserByUuid("uuid");

        // then
        assertNotNull(result);
        assertEquals(userRecord, result);

        verify(userRepository, times(1)).findByUuidEquals(eq("uuid"));
        verify(userMapper, times(1)).toRecord(eq(userEntity));
    }

    @Test
    void createUser() {
        // given
        CreateUserRequestRecord input = new CreateUserRequestRecord("fn", "ln", "em", LocalDate.of(1990, 1, 1));

        UserEntity output = new UserEntity("uuid", "em", "fn", "ln", LocalDate.of(1990, 1, 1));
        when(userRepository.saveAndFlush(any(UserEntity.class))).thenReturn(output);
        UserRecord record = new UserRecord(UUID.randomUUID(), "fn", "ln", "em", LocalDate.of(1990, 1, 1));
        when(userMapper.toRecord(output)).thenReturn(record);

        // when
        UserRecord result = userService.createUser(input);
        // then
        assertNotNull(result);
        assertEquals(record, result);

        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
        verify(userMapper, times(1)).toRecord(any(UserEntity.class));
    }

    @Test
    void updateUserByUuidNull() {
        // given
        when(userRepository.findByUuidEquals(isNull())).thenReturn(null);
        // when
        UserAppNotFoundException result = assertThrows(UserAppNotFoundException.class, () ->
                userService.updateUser(null, null));

        // then
        assertEquals("User not found with uuid: [null]", result.getMessage());
    }

    @Test
    void updateUserByUuidNonExistingUser() {
        // given
        when(userRepository.findByUuidEquals(anyString())).thenReturn(null);
        // when
        UserAppNotFoundException result = assertThrows(UserAppNotFoundException.class, () ->
                userService.updateUser("nonExistingUuid", null));

        // then
        assertEquals("User not found with uuid: [nonExistingUuid]", result.getMessage());
    }

    @Test
    void updateUserByUuidExistingUserButNoInput() {
        // given
        UserEntity existingUser = new UserEntity();
        existingUser.setUuid("uuid");
        existingUser.setFirstName("fn");
        existingUser.setLastName("ln");
        existingUser.setEmail("em");

        when(userRepository.findByUuidEquals(eq("uuid"))).thenReturn(existingUser);
        UserRecord userRecord = new UserRecord(UUID.randomUUID(), "fn", "ln", "em", LocalDate.of(1990, 1, 1));
        when(userMapper.toRecord(existingUser)).thenReturn(userRecord);

        // when
        UserRecord result = userService.updateUser("uuid", null);

        // then
        assertNotNull(result);
        assertEquals(userRecord, result);
        assertEquals("fn", result.firstName());
        assertEquals("ln", result.lastName());
        assertEquals("em", result.email());
        assertEquals(LocalDate.of(1990,1,1), result.birthdate());

        verify(userMapper, times(1)).toRecord(existingUser);
        verify(userRepository, never()).saveAndFlush(any(UserEntity.class));
    }

    @Test
    void updateUserByUuidExistingUser() {
        // given
        UserEntity existingUser = new UserEntity();
        existingUser.setUuid("uuid");
        existingUser.setFirstName("fn");
        existingUser.setLastName("ln");
        existingUser.setEmail("em");

        when(userRepository.findByUuidEquals(eq("uuid"))).thenReturn(existingUser);
        UserRecord userRecord = new UserRecord(UUID.randomUUID(), "fn2", "ln2", "em2", LocalDate.of(1990, 1, 1));
        when(userRepository.saveAndFlush(existingUser)).thenReturn(existingUser);
        when(userMapper.toRecord(any(UserEntity.class))).thenReturn(userRecord);

        // when
        UserRecord result = userService.updateUser("uuid", new UpdateUserRequestRecord("fn2", "ln2", "em2"));

        // then
        assertNotNull(result);
        assertEquals(userRecord, result);
        assertEquals("fn2", result.firstName());
        assertEquals("ln2", result.lastName());
        assertEquals("em2", result.email());
        assertEquals(LocalDate.of(1990,1,1), result.birthdate());

        verify(userMapper, times(1)).toRecord(existingUser);
        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
    }

    @Test
    void searchUsers() {
        // given
        when(userRepository.findAll(ArgumentMatchers.<Specification<UserEntity>>any(), any(Pageable.class))).thenReturn(Mockito.<Page>mock(Page.class));

        // when
        userService.searchUsers(new UserSearchQueryParamsRecord("fn", "ln", "em", LocalDate.of(1990,1,1), UserSearchOrderByEnum.EMAIL, OrderDirectionEnum.ASC, 2, 5));

        // then
        verify(userRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void deleteUserByUuidNonExistingUser() {
        // given
        when(userRepository.findByUuidEquals(anyString())).thenReturn(null);
        // when
        UserAppNotFoundException result = assertThrows(UserAppNotFoundException.class, () ->
                userService.deleteUserByUuid("nonExistingUuid"));

        // then
        assertEquals("User not found with uuid: [nonExistingUuid]", result.getMessage());
    }

    @Test
    void deleteUserByUuid() {
        // given
        UserEntity existingUser = new UserEntity();
        existingUser.setUuid("uuid");
        existingUser.setFirstName("fn");
        existingUser.setLastName("ln");
        existingUser.setEmail("em");

        when(userRepository.findByUuidEquals(eq("uuid"))).thenReturn(existingUser);
        // when
        userService.deleteUserByUuid("uuid");
        // then
        verify(userRepository, times(1)).deleteByUuidEquals(eq("uuid"));
    }
}