package org.example.springdemo.application.repository;

import org.example.springdemo.application.entity.UserEntity;
import org.example.springdemo.application.enums.OrderDirectionEnum;
import org.example.springdemo.application.enums.UserSearchOrderByEnum;

import java.time.LocalDate;
import java.util.List;

public interface UserSearchRepository {
    List<UserEntity> findUserEntities(String firstName, String lastName, String email, LocalDate birthdate, UserSearchOrderByEnum orderBy, OrderDirectionEnum orderDirection, int page, int limit);
}
