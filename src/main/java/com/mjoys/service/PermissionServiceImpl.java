package com.mjoys.service;


import com.mjoys.entity.Permission;
import com.mjoys.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> getPermissionByUserId(Integer userId) {
        return permissionMapper.getPermissionByUserId(userId);
    }
}