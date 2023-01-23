package com.mazhar.blogs.app.services;

import com.mazhar.blogs.app.payloads.UserDto;
import com.mazhar.blogs.app.payloads.UserResponse;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(Integer userId);

    UserResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void deleteUserById(Integer userId);
}
