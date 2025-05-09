package org.example.springdemo.application.validators;

public class ParamValidator
{

    public static boolean isNotNullAndNotBlank(String param) {
        return param != null && !param.isBlank();
    }
}
