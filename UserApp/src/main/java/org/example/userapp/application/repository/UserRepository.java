package org.example.userapp.application.repository;

import org.example.userapp.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByUuidEquals(@Param("uuid") final String uuid);
    void deleteByUuidEquals(@Param("uuid") final String uuid);
}
