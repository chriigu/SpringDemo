package org.example.userapp.application.enums;

public enum UserSearchOrderByEnum {

    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    BIRTHDATE("birthdate");

    private final String value;

    UserSearchOrderByEnum(String value) {
        this.value = value;
    }

    public String getEnumName() {
        return value;
    }

    public String getValue() {
        return value;
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
