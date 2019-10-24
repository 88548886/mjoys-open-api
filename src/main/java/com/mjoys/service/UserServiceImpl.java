package com.mjoys.service;

import com.mjoys.entity.User;
import com.mjoys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.findUserByUserName(username);
        return user;
    }

}