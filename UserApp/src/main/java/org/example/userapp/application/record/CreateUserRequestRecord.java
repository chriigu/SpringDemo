package org.example.userapp.application.record;

import java.time.LocalDate;

public record CreateUserRequestRecord(String firstName,
                                      String lastName,
                                      String email,
                                      LocalDate birthDate) {}
