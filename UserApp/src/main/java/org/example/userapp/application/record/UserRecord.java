package org.example.userapp.application.record;

import java.time.LocalDate;
import java.util.UUID;

public record UserRecord(UUID uuid, String firstName, String lastName, String email, LocalDate birthdate) {
}
