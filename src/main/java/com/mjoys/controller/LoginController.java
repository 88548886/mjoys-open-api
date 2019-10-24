package com.mjoys.controller;

import com.google.common.collect.Lists;
import com.mjoys.auth.jwt.JwtUtil;
import com.mjoys.constant.StatusCode;
import com.mjoys.dto.CommomResponseDto;
import com.mjoys.entity.User;
import com.mjoys.service.IPermissionService;
import com.mjoys.service.IRoleService;
import com.mjoys.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;


    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;


    @PostMapping("/login")
    public CommomResponseDto login(String username, String password) {

        CommomResponseDto<String> res = new CommomResponseDto();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            res.setRetCode(StatusCode.PARAM_ERROR);
            return res;
        }

        // 查询数据库中的帐号信息

        //验证用户
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));

        User user = (User) subject.getPrincipal();

//        List<String> roleStrList = Lists.newArrayList();
//        List<String> permissionsStrList = Lists.newArrayList();
//        if (null != user.getRoles()) {
//            user.getRoles().forEach(i -> roleStrList.add(i.getRoleName()));
//        }
//        if (null != user.getRoles()) {
//            user.getPermissions().forEach(i -> permissionsStrList.add(i.getPermission()));
//        }

        res.Success(JwtUtil.issue(user.getId(), user.getUsername(), user.getRoles(), user.getPermissions()));

        return res;
    }
}
