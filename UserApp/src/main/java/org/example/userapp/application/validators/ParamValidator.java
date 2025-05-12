package org.example.userapp.application.validators;

public class ParamValidator
{
    private ParamValidator() {}

    public static boolean isNotNullAndNotBlank(final String param) {
        return param != null && !param.isBlank();
    }
}
