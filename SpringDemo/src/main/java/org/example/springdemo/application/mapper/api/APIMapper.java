package org.example.springdemo.application.mapper.api;

import org.example.springdemo.application.enums.OrderDirectionEnum;
import org.example.springdemo.application.enums.UserSearchOrderByEnum;
import org.example.springdemo.application.openapi.model.OAOrderDirectionEnum;
import org.example.springdemo.application.openapi.model.OAUserDto;
import org.example.springdemo.application.openapi.model.OAUserSearchOrderEnum;
import org.example.springdemo.application.record.UserRecord;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class APIMapper {


    public OAUserDto mapUserToAPIUserDtoResponseEntity (final UserRecord userRecord) {
        OAUserDto userDto = new OAUserDto();
        userDto.setUuid(userRecord.uuid());
        userDto.setEmail(userRecord.email());
        userDto.setFirstName(userRecord.firstName());
        userDto.setLastName(userRecord.lastName());
        userDto.setBirthdate(userRecord.birthdate());
        return userDto;
    }

    public UserSearchOrderByEnum mapUserSearchOrderEnum(OAUserSearchOrderEnum userSearchOrderEnum) {
        switch (userSearchOrderEnum) {
            case FIRST_NAME -> {
                return UserSearchOrderByEnum.FIRST_NAME;
            }
            case LAST_NAME -> {
                return UserSearchOrderByEnum.LAST_NAME;
            }
            case EMAIL -> {
                return UserSearchOrderByEnum.EMAIL;
            }
            case BIRTHDATE -> {
                return UserSearchOrderByEnum.BIRTHDATE;
            }
        }
        throw new IllegalArgumentException("Invalid userSearchOrderEnum: " + userSearchOrderEnum);
    }

    public OrderDirectionEnum mapOrderDirectionEnum(OAOrderDirectionEnum orderDirectionEnum) {
        if (Objects.requireNonNull(orderDirectionEnum) == OAOrderDirectionEnum.ASC) {
            return OrderDirectionEnum.ASC;
        } else if (orderDirectionEnum == OAOrderDirectionEnum.DESC) {
            return OrderDirectionEnum.DESC;
        }
        throw new IllegalArgumentException("Invalid orderDirectionEnum: " + orderDirectionEnum);
    }
}
