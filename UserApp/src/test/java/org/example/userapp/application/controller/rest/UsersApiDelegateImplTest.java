package org.example.userapp.application.controller.rest;

import jakarta.validation.Valid;
import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;
import org.example.userapp.application.mapper.api.APIMapper;
import org.example.userapp.application.openapi.model.*;
import org.example.userapp.application.record.*;
import org.example.userapp.application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

class UsersApiDelegateImplTest {

    private UserService userService;
    private APIMapper apiMapper;
    private UsersApiDelegateImpl usersApiDelegate;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        apiMapper = mock(APIMapper.class);
        usersApiDelegate = new UsersApiDelegateImpl(userService, apiMapper);
    }

    @Test
    void getUserByUuidTestNull() {
        // given
        UUID input = UUID.randomUUID();
        when(userService.findUserByUuid(anyString())).thenReturn(null);
        when(apiMapper.mapUserRecordToOAUserDto(isNull())).thenReturn(null);

        // when
        ResponseEntity<OAUserDto> result = usersApiDelegate.getUserByUuid(input);

        // then
        assertNotNull(result);
        assertNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getUserByUuidTest() {
        // given
        UUID input = UUID.randomUUID();
        UserRecord userRecord = new UserRecord(UUID.fromString(input.toString()), "fn", "ln", "em", LocalDate.of(1990,1,1));
        when(userService.findUserByUuid(anyString())).thenReturn(userRecord);
        OAUserDto userDto = new OAUserDto();
        userDto.setUuid(input);
        userDto.setEmail("em");
        userDto.setFirstName("fn");
        userDto.setLastName("ln");
        userDto.setBirthdate(LocalDate.of(1990,1,1));
        when(apiMapper.mapUserRecordToOAUserDto(userRecord)).thenReturn(userDto);

        // when
        ResponseEntity<OAUserDto> result = usersApiDelegate.getUserByUuid(input);

        // then
        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(result.getBody());
        assertEquals(input.toString(), result.getBody().getUuid().toString());
        assertEquals("em", result.getBody().getEmail());
        assertEquals("fn", result.getBody().getFirstName());
        assertEquals("ln", result.getBody().getLastName());
        assertEquals(LocalDate.of(1990,1,1), result.getBody().getBirthdate());
    }

    @Test
    void createUserNull() {
        // given
        OACreateUserRequest input = new OACreateUserRequest();
        input.setEmail("em");
        input.setFirstName("fn");
        input.setLastName("ln");
        input.setBirthdate(LocalDate.of(1990,1,1));

        CreateUserRequestRecord createUserRequestRecord = new CreateUserRequestRecord("fn", "ln", "em", LocalDate.of(1990,1,1));
        when(apiMapper.mapOACreateUserRequestToCreateUserRequestRecord(input)).thenReturn(createUserRequestRecord);
        when(userService.createUser(createUserRequestRecord)).thenReturn(null);
        when(apiMapper.mapUserRecordToOAUserDto(isNull())).thenReturn(null);

        // when
        ResponseEntity<OAUserDto> result = usersApiDelegate.createUser(input);

        // then
        assertNotNull(result);
        assertNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createUser() {
        // given
        OACreateUserRequest input = new OACreateUserRequest();
        input.setEmail("em");
        input.setFirstName("fn");
        input.setLastName("ln");
        input.setBirthdate(LocalDate.of(1990,1,1));

        CreateUserRequestRecord createUserRequestRecord = new CreateUserRequestRecord("fn", "ln", "em", LocalDate.of(1990,1,1));
        when(apiMapper.mapOACreateUserRequestToCreateUserRequestRecord(input)).thenReturn(createUserRequestRecord);
        UUID uuid = UUID.randomUUID();
        UserRecord userRecord = new UserRecord(uuid, "fn", "ln", "em", LocalDate.of(1990,1,1));
        when(userService.createUser(createUserRequestRecord)).thenReturn(userRecord);

        OAUserDto userDto = new OAUserDto();
        userDto.setUuid(uuid);
        userDto.setEmail("em");
        userDto.setFirstName("fn");
        userDto.setLastName("ln");
        userDto.setBirthdate(LocalDate.of(1990,1,1));
        when(apiMapper.mapUserRecordToOAUserDto(userRecord)).thenReturn(userDto);

        // when
        ResponseEntity<OAUserDto> result = usersApiDelegate.createUser(input);

        // then
        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(result.getBody());
        assertEquals(uuid.toString(), result.getBody().getUuid().toString());
        assertEquals("em", result.getBody().getEmail());
        assertEquals("fn", result.getBody().getFirstName());
        assertEquals("ln", result.getBody().getLastName());
        assertEquals(LocalDate.of(1990,1,1), result.getBody().getBirthdate());
    }

    @Test
    void deleteUser() {
        // given
        // when
        UUID uuid = UUID.randomUUID();
        ResponseEntity<Void> result = usersApiDelegate.deleteUser(uuid);

        // then
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        verify(userService, times(1)).deleteUserByUuid(uuid.toString());
    }

    @Test
    void updateUserByUuidNull() {
        // given
        UUID inputUUID = UUID.randomUUID();

        OAUpdateUserRequest input = new OAUpdateUserRequest();
        input.setEmail("em");
        input.setFirstName("fn");
        input.setLastName("ln");

        UpdateUserRequestRecord updateUserRequestRecord = new UpdateUserRequestRecord("fn", "ln", "em");
        when(apiMapper.mapOAUpdateUserRequestToUpdateUserRequestRecord(input)).thenReturn(updateUserRequestRecord);
        when(userService.updateUser(inputUUID.toString(), updateUserRequestRecord)).thenReturn(null);
        when(apiMapper.mapUserRecordToOAUserDto(isNull())).thenReturn(null);

        // when
        ResponseEntity<OAUserDto> result = usersApiDelegate.updateUserByUuid(inputUUID, input);

        // then
        assertNotNull(result);
        assertNull(result.getBody());
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void updateUserByUuid() {
        // given
        UUID inputUUID = UUID.randomUUID();

        OAUpdateUserRequest input = new OAUpdateUserRequest();
        input.setEmail("em");
        input.setFirstName("fn");
        input.setLastName("ln");

        UpdateUserRequestRecord updateUserRequestRecord = new UpdateUserRequestRecord("fn", "ln", "em");
        when(apiMapper.mapOAUpdateUserRequestToUpdateUserRequestRecord(input)).thenReturn(updateUserRequestRecord);
        UserRecord userRecord = new UserRecord(UUID.fromString(inputUUID.toString()), "fn", "ln", "em", LocalDate.of(1990,1,1));
        when(userService.updateUser(inputUUID.toString(), updateUserRequestRecord)).thenReturn(userRecord);
        OAUserDto userDto = new OAUserDto();
        userDto.setUuid(inputUUID);
        userDto.setEmail("em");
        userDto.setFirstName("fn");
        userDto.setLastName("ln");
        userDto.setBirthdate(LocalDate.of(1990,1,1));
        when(apiMapper.mapUserRecordToOAUserDto(userRecord)).thenReturn(userDto);

        // when
        ResponseEntity<OAUserDto> result = usersApiDelegate.updateUserByUuid(inputUUID, input);

        // then
        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(result.getBody());
        assertEquals(inputUUID.toString(), result.getBody().getUuid().toString());
        assertEquals("em", result.getBody().getEmail());
        assertEquals("fn", result.getBody().getFirstName());
        assertEquals("ln", result.getBody().getLastName());
        assertEquals(LocalDate.of(1990,1,1), result.getBody().getBirthdate());
    }

    @Test
    void findUsersAllParametersNullOrBlank() {
        // given
        // when
        ResponseEntity<OAUserSearchResult> result = usersApiDelegate.findUsers(OAOrderDirectionEnum.ASC, OAUserSearchOrderEnum.FIRST_NAME, 10, 100, "  ", " ", " ", null);

        // then
        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getPageNumber());
        assertEquals(1, result.getBody().getTotalPages());
        assertEquals(0, result.getBody().getTotalResults());
        assertEquals(100, result.getBody().getPageSize());

        verify(userService, never()).searchUsers(any());
        verify(userService, never()).searchUsers(isNull());
        verify(apiMapper, never()).mapUserSearchResultRecordToOAUserSearchResult(any());
        verify(apiMapper, never()).mapUserSearchResultRecordToOAUserSearchResult(isNull());
        verify(apiMapper, never()).mapOAUserSearchOrderEnumToUserSearchOrderByEnum(any());
        verify(apiMapper, never()).mapOAUserSearchOrderEnumToUserSearchOrderByEnum(isNull());
        verify(apiMapper, never()).mapOAOrderDirectionEnumToOrderDirectionEnum(any());
        verify(apiMapper, never()).mapOAOrderDirectionEnumToOrderDirectionEnum(isNull());
    }

    @Test
    void findUsersAllParameters() {
        // given
        when(apiMapper.mapOAUserSearchOrderEnumToUserSearchOrderByEnum(any())).thenReturn(UserSearchOrderByEnum.FIRST_NAME);
        when(apiMapper.mapOAOrderDirectionEnumToOrderDirectionEnum(any())).thenReturn(OrderDirectionEnum.ASC);
        UserSearchQueryParamsRecord userSearchQueryParamsDto = new UserSearchQueryParamsRecord("fn", "ln", "em", LocalDate.of(1990,1,1), UserSearchOrderByEnum.FIRST_NAME, OrderDirectionEnum.ASC, 10, 100);
        UUID uuid1 = UUID.randomUUID();
        UserRecord user1 = new UserRecord(uuid1, "fn1", "ln1", "em1", LocalDate.of(1990,1,1));
        UUID uuid2 = UUID.randomUUID();
        UserRecord user2 = new UserRecord(uuid2, "fn2", "ln2", "em2", LocalDate.of(1990,1,2));
        ArrayList<UserRecord> userRecords = new ArrayList<>();
        userRecords.add(user1);
        userRecords.add(user2);

        UserSearchResultRecord userSearchResultRecord = new UserSearchResultRecord(
                userRecords,
                5, 10, 100,1
        );
        when(userService.searchUsers(userSearchQueryParamsDto)).thenReturn(userSearchResultRecord);
        OAUserSearchResult userSearchResult = new OAUserSearchResult();
        userSearchResult.setTotalResults(5);
        userSearchResult.setPageSize(100);
        userSearchResult.setPageNumber(1);
        userSearchResult.setTotalPages(1);
        List<OAUserDto> userDtos = new ArrayList<>();
        OAUserDto userDto1 = new OAUserDto();
        userDto1.setUuid(uuid1);
        userDto1.setFirstName("fn1");
        userDto1.setLastName("ln1");
        userDto1.setBirthdate(LocalDate.of(1990,1,1));
        userDto1.setEmail("em1");

        OAUserDto userDto2 = new OAUserDto();
        userDto2.setUuid(uuid2);
        userDto2.setFirstName("fn2");
        userDto2.setLastName("ln2");
        userDto2.setBirthdate(LocalDate.of(1990,1,2));
        userDto2.setEmail("em2");

        userDtos.add(userDto1);
        userDtos.add(userDto2);
        userSearchResult.setResultList(userDtos);

        when(apiMapper.mapUserSearchResultRecordToOAUserSearchResult(userSearchResultRecord)).thenReturn(userSearchResult);

        // when
        ResponseEntity<OAUserSearchResult> result = usersApiDelegate.findUsers(OAOrderDirectionEnum.ASC, OAUserSearchOrderEnum.FIRST_NAME, 10, 100, "fn", "ln", "em", LocalDate.of(1990,1,1));

        // then
        assertNotNull(result);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getResultList());
        assertEquals(2, result.getBody().getResultList().size());
        assertEquals(uuid1, result.getBody().getResultList().get(0).getUuid());
        assertEquals(uuid2, result.getBody().getResultList().get(1).getUuid());
        assertEquals("fn1", result.getBody().getResultList().get(0).getFirstName());
        assertEquals("ln1", result.getBody().getResultList().get(0).getLastName());
        assertEquals("em1", result.getBody().getResultList().get(0).getEmail());
        assertEquals(LocalDate.of(1990,1,1), result.getBody().getResultList().get(0).getBirthdate());


        assertEquals("fn2", result.getBody().getResultList().get(1).getFirstName());
        assertEquals("ln2", result.getBody().getResultList().get(1).getLastName());
        assertEquals("em2", result.getBody().getResultList().get(1).getEmail());
        assertEquals(LocalDate.of(1990,1,2), result.getBody().getResultList().get(1).getBirthdate());

        assertEquals(1, result.getBody().getPageNumber());
        assertEquals(1, result.getBody().getTotalPages());
        assertEquals(5, result.getBody().getTotalResults());
        assertEquals(100, result.getBody().getPageSize());

        verify(userService, times(1)).searchUsers(userSearchQueryParamsDto);
        verify(apiMapper, times(1)).mapUserSearchResultRecordToOAUserSearchResult(userSearchResultRecord);
        verify(apiMapper, times(1)).mapOAUserSearchOrderEnumToUserSearchOrderByEnum(OAUserSearchOrderEnum.FIRST_NAME);
        verify(apiMapper, times(1)).mapOAOrderDirectionEnumToOrderDirectionEnum(OAOrderDirectionEnum.ASC);
    }
}