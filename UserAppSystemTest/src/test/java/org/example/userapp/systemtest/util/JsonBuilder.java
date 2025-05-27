package org.example.userapp.systemtest.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonBuilder {

    public static String v(String name, String value) {
        return value == null ? "\"" + name + "\": " + value : "\"" + name + "\": \"" + value + "\"";
    }


    public static String v(String name, Integer value) {
        return "\"" + name + "\": " + value;
    }

    public static String v(String name, Boolean value) {
        return "\"" + name + "\": " + value;
    }

    public static String v(String name, LocalDate value) {
        return v(name, value == null ? null : value.format(DateTimeFormatter.ISO_DATE));
    }

    public static String v(String name, LocalDateTime value) {
        return v(name, value == null ? null : value.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    public static String a(String name, String... values) {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append(": [");
        for (String value : values) {
            sb
                    .append(value)
                    .append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    public static String o(String name, String... values) {
        StringBuilder sb = new StringBuilder();
        if(name != null) {
            sb.append("\"")
                    .append(name)
                    .append("\"")
                    .append(": {");
        } else {
            sb.append("{");
        }
        for (String value : values) {
            sb
                    .append(value)
                    .append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("},");
        return sb.substring(0, sb.length() - 1);
    }
}
