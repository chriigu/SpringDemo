package org.example.service;

import org.example.entity.UserEntity;
import org.example.mapper.internal.UserMapper;
import org.example.record.UserRecord;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRecord findUserByUuid(String uuid) {
        UserEntity byUuid = userRepository.findUserByUuid(uuid);
        return UserMapper.toRecord(byUuid);
    }
}
