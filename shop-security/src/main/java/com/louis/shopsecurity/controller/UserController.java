package com.louis.shopsecurity.controller;

import com.louis.shopsecurity.controller.result.BaseResult;
import com.louis.shopsecurity.persistence.entity.User;
import com.louis.shopsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<BaseResult> register (@RequestBody User user) {

        user.setRoles(DEFAULT_ROLE);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userService.register(user);

        BaseResult result = new BaseResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("註冊成功");

        return ResponseEntity.ok(result);
    }
}