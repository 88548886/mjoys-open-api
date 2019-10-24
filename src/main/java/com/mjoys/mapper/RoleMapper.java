package com.mjoys.mapper;

import com.mjoys.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> findRoleByUserId(Integer userId);
}
