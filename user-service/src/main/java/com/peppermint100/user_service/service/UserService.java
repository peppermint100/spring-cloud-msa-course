package com.peppermint100.user_service.service;

import com.peppermint100.user_service.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
