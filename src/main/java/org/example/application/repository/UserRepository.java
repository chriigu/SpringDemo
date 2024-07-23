package org.example.application.repository;

import org.example.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    UserEntity findUserById(Long id);
    UserEntity findByUuidEquals(@Param("uuid") String uuid);
//    UserEntity findUserByUuid(@Param("UUID") String uuid);
//    List<UserEntity> findAll();

}
