package com.mjoys.controller;


import com.google.common.collect.Lists;
import com.mjoys.dto.CommomResponseDto;
import com.mjoys.entity.User;
import com.mjoys.service.IUserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequiresRoles(value = {"admin"})
    @GetMapping("/list")
    public CommomResponseDto<List<User>> getUserList() {
        return new CommomResponseDto<List<User>>().setData(Lists.newArrayList(new User().setId(1).setPassword("1").setUsername("abc"),
                new User().setId(2).setPassword("2").setUsername("def")));
    }


    @GetMapping("/detail")
    @RequiresPermissions(value = {"user:get"})
    public CommomResponseDto<List<User>> getUser(@RequestParam(value = "id") int id) {
        return new CommomResponseDto<List<User>>().setData(Lists.newArrayList(new User().setId(id).setPassword("xxx")));
    }
}
