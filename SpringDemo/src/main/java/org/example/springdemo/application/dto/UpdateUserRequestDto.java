package org.example.springdemo.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
}
