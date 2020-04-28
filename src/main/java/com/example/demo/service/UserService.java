package com.example.demo.service;

import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public Paging findAllUser(int page);

    public UserDto getUserById(Long id);

    public Paging searchFTS(int page, String searchKey);

    public void createUser(UserCreateRequest userCreateRequest);

    public void updateUser(Long id, UserUpdateRequest userUpdateRequest);

    public void deleteUser(Long id);
}
