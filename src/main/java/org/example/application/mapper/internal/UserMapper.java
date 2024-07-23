package org.example.application.mapper.internal;

import org.example.application.entity.UserEntity;
import org.example.application.record.UserRecord;

public class UserMapper {

    public static UserRecord toRecord(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserRecord(entity.getUuid(), entity.getEmail(), entity.getFirstName(), entity.getLastName());
    }

    public static UserEntity toEntity(UserRecord record) {
        return new UserEntity(record.uuid(), record.email(), record.firstName(), record.lastName());
    }
}
