package org.example.springdemo.application.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.springdemo.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserSearchRepository {
    UserEntity findByUuidEquals(@Param("uuid") String uuid);
    void deleteByUuidEquals(@Param("uuid") String uuid);
}
