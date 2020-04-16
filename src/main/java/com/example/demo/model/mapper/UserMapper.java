package com.example.demo.model.mapper;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setAddress(user.getAddress());
        userDto.setPhone(user.getPhone());
        userDto.setAvatar(user.getAvatar());
        userDto.setEmail(user.getEmail());
        userDto.setDateCreated(user.getDateCreated());
        userDto.setDateUpdated(user.getDateUpdated());
        userDto.setRole(user.getRole().getRoleName());
        return userDto;
    }

    public static User toUser(UserCreateRequest ucr) {
        User user = new User();
        user.setUserName(ucr.getName());
        user.setEmail(ucr.getEmail());
        user.setDateCreated(new Date());
        user.setDateUpdated(new Date());

        String hash= BCrypt.hashpw(ucr.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);

        return user;
    }

    public static User toUser(UserUpdateRequest uur, Long id, Date date, Role role, String password) {
        User user = new User();

        user.setId(id);
        user.setUserName(uur.getName());
        user.setAddress(uur.getAddress());
        user.setPhone(uur.getPhone());
        user.setAvatar(uur.getAvatar());
        user.setEmail(uur.getEmail());
        user.setPassword(password);
        user.setRole(role);
        user.setDateCreated(date);
        user.setDateUpdated(new Date());

//        String hash= BCrypt.hashpw(uur.getPassword(),BCrypt.gensalt(12));
//        user.setPassword(hash);

        return user;
    }
}
