package com.mjoys.service;

import com.mjoys.entity.Permission;

import java.util.List;

public interface IPermissionService {

    List<Permission> getPermissionByUserId(Integer userId);
}