package org.example.springdemo.application.mapper.internal;

import org.example.springdemo.application.entity.UserEntity;
import org.example.springdemo.application.record.UserRecord;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    private UserMapper() {
    }

    public UserRecord toRecord(final UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserRecord(UUID.fromString(entity.getUuid()), entity.getEmail(), entity.getFirstName(), entity.getLastName(), entity.getBirthdate());
    }

    public UserEntity toEntity(final UserRecord userRecord) {
        if (userRecord == null) {
            return null;
        }
        return new UserEntity(userRecord.uuid().toString(), userRecord.email(), userRecord.firstName(), userRecord.lastName(), userRecord.birthdate());
    }
}
