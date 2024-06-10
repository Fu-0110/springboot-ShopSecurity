package com.louis.shopsecurity.service;

import com.louis.shopsecurity.persistence.entity.User;
import com.louis.shopsecurity.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String token)
            throws UsernameNotFoundException {
        User user = userMapper.findByUsername(token);
        if (user == null) {
            user = userMapper.findByMobile(token);
            if(user == null)
                throw new UsernameNotFoundException("用户不存在!");
        }
        return user;
    }

    public User register(User user) {
        userMapper.saveUser(user);
        return user;
    }
}