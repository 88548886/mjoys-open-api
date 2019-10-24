package com.mjoys.mapper;

import com.mjoys.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> getPermissionByUserId(Integer userId);
}
