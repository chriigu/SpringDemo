package org.example.mapper.internal;

import org.example.entity.UserEntity;
import org.example.record.UserRecord;

public class UserMapper {

    public static UserRecord toRecord(UserEntity entity) {
        return new UserRecord(entity.getUuid(), entity.getEmail(), entity.getFirstName(), entity.getLastName());
    }

    public static UserEntity toEntity(UserRecord record) {
        return new UserEntity(record.uuid(), record.email(), record.firstName(), record.lastName());
    }
}
