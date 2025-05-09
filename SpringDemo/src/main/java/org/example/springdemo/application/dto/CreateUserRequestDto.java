package org.example.springdemo.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

}
