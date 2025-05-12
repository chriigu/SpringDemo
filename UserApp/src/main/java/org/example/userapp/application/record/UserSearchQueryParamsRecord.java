package org.example.userapp.application.record;

import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;

import java.time.LocalDate;

public record UserSearchQueryParamsRecord(String firstName, String lastName, String email, LocalDate birthdate,
                                          UserSearchOrderByEnum orderBy, OrderDirectionEnum orderDirection, int page,
                                          int limit) {
}