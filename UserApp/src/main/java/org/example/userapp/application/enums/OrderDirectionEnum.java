package org.example.userapp.application.enums;

public enum OrderDirectionEnum {

    ASC("ASC"),
    DESC("DESC");

    private final String value;

    OrderDirectionEnum(String value) {
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

    public static OrderDirectionEnum fromValue(String value) {
        for (OrderDirectionEnum b : OrderDirectionEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
