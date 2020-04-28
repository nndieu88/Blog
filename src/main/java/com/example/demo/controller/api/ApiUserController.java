package com.example.demo.controller.api;

import com.example.demo.model.api.BasicApiResult;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admins/users")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            UserDto userDto = userService.getUserById(id);

            result.setSuccess(true);
            result.setMessage("Successfull");
            result.setData(userDto);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest user) {
        BasicApiResult result = new BasicApiResult();
        try {
            userService.createUser(user);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @PathVariable Long id, @RequestBody UserUpdateRequest user) {
        BasicApiResult result = new BasicApiResult();
        try {
            userService.updateUser(id, user);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            userService.deleteUser(id);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
