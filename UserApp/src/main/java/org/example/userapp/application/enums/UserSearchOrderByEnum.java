package org.example.userapp.application.enums;

import lombok.Getter;
import org.example.userapp.application.entity.UserEntity_;

@Getter
public enum UserSearchOrderByEnum {

    FIRST_NAME(UserEntity_.FIRST_NAME),
    LAST_NAME(UserEntity_.LAST_NAME),
    EMAIL(UserEntity_.EMAIL),
    BIRTHDATE(UserEntity_.BIRTHDATE);

    private final String value;

    UserSearchOrderByEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static UserSearchOrderByEnum fromValue(String value) {
        for (UserSearchOrderByEnum b : UserSearchOrderByEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
