package com.mjoys.service;

import com.mjoys.entity.User;

public interface IUserService {

    User findUserByUsername(String username);
}