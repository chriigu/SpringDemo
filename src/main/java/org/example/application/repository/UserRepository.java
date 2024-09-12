package org.example.application.repository;

import org.example.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    UserEntity findUserById(Long id);
    UserEntity findByUuidEquals(@Param("uuid") String uuid);
    Collection<UserEntity> findAllByEmailOrFirstNameOrLastNameContainingIgnoreCase(@Param("email") String email
    , @Param("firstName") String firstName, @Param("lastName") String lastName);

}
