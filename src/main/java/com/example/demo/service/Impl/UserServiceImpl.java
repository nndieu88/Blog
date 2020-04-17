package com.example.demo.service.Impl;

import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserDto> findAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toUserDto(user));
        }
        return userDtos;
    }

    @Override
    public List<User> listUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("No user found");
        }

        return UserMapper.toUserDto(user.get());
    }

    @Override
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        User user = userRepository.findUserByEmail(userCreateRequest.getEmail());
        if (user != null) {
            throw new DuplicateRecordException("Email already exists in the system");
        }
        User user1 = UserMapper.toUser(userCreateRequest);
        user1.setRole(roleRepository.findById(2L).get());
        try {

            userRepository.save(user1);
        } catch (Exception ex) {
            throw new InternalServerException("Can't create user");
        }

        return UserMapper.toUserDto(user1);
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("Not found user");
        }
        User user1 = UserMapper.toUser(userUpdateRequest, id, user.get().getDateCreated(), user.get().getRole(), user.get().getPassword());
        try {
            userRepository.save(user1);
        } catch (Exception ex) {
            throw new InternalServerException("Can't update user");
        }
        return UserMapper.toUserDto(user1);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("Not found user");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can't delete user");
        }
    }
}
