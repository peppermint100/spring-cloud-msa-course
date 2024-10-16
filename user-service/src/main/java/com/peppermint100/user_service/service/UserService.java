package com.peppermint100.user_service.service;

import com.peppermint100.user_service.dto.UserDto;
import com.peppermint100.user_service.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String userId);
    Iterable<UserEntity> getUserByAll();
}
