package org.example.userapp.application.mapper.internal;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.record.UserRecord;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public UserMapper() {
    }

    public UserRecord toRecord(final UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserRecord(
                UUID.fromString(entity.getUuid()),
                entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                entity.getBirthdate());
    }
}
