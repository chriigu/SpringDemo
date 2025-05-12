package org.example.userapp.application.repository;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.record.UserSearchQueryParamsRecord;

import java.util.List;

public interface UserSearchRepository {
    List<UserEntity> findUserEntities(final UserSearchQueryParamsRecord userSearchQueryParamsDto);
}
