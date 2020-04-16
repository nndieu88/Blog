package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<UserDto> findAllUser();

    public List<User> listUser();

    public UserDto getUserById(Long id);

    public UserDto createUser(UserCreateRequest userCreateRequest);

    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest);

    public void deleteUser(Long id);
}
