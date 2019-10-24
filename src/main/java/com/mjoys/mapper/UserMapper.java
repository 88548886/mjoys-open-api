package com.mjoys.mapper;

import com.mjoys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findUserByUserName(@Param("username") String username);
}
