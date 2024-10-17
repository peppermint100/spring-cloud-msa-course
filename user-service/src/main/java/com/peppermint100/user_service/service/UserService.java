package com.peppermint100.user_service.service;

import com.peppermint100.user_service.dto.UserDto;
import com.peppermint100.user_service.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String userId);
    Iterable<UserEntity> getUserByAll();
    UserDto getUserDetailsByEmail(String email);
}
