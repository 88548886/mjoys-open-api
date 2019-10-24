package com.mjoys.service;


import com.mjoys.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getRoleByUserId(Integer userId);
}