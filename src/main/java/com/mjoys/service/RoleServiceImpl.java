package com.mjoys.service;


import com.mjoys.entity.Role;
import com.mjoys.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleByUserId(Integer userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}