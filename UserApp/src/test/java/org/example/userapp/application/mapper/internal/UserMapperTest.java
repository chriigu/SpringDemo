package org.example.userapp.application.mapper.internal;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.record.UserRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void toRecordNull() {
        // given
        UserEntity input = null;
        // when
        UserRecord result = mapper.toRecord(input);
        // then
        assertNull(result);
    }

    @Test
    void toRecord() {
        // given
        UserEntity input = new UserEntity();
        String uuid = UUID.randomUUID().toString();
        input.setUuid(uuid);
        input.setFirstName("fn");
        input.setLastName("ln");
        input.setEmail("em");
        input.setBirthdate(LocalDate.of(1990,1,1));

        // when
        UserRecord result = mapper.toRecord(input);
        // then
        assertNotNull(result);
        assertNotNull(result.uuid());
        assertEquals(uuid, result.uuid().toString());
        assertEquals("fn", result.firstName());
        assertEquals("ln", result.lastName());
        assertEquals("em", result.email());
        assertEquals(LocalDate.of(1990,1,1), result.birthdate());
    }
}