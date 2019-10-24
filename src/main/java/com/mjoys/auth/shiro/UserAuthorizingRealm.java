package com.mjoys.auth.shiro;

import com.google.common.collect.Lists;
import com.mjoys.entity.Permission;
import com.mjoys.entity.Role;
import com.mjoys.entity.User;
import com.mjoys.service.IPermissionService;
import com.mjoys.service.IRoleService;
import com.mjoys.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }


    //因为用JWT，是无状态的，所以请求是不会要求携带cookie信息，
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.findUserByUsername(usernamePasswordToken.getUsername());
        if (null == user) {
            throw new AuthenticationException("用户异常");
        }
        //按正常的逻辑SimpleAuthenticationInfo应该给MD5后的密码，但是这里shiro框架会对比AuthenticationToken和SimpleAuthenticationInfo的用户名和密码
        //在这之前我们已经校验了，所以这里直接回没有MD5的让它校验通过,不然在login时还要读一次数据库
        List<Role> roleList = roleService.getRoleByUserId(user.getId());
        List<Permission> permissionList = permissionService.getPermissionByUserId(user.getId());

        List<String> roles = Lists.newArrayList();
        List<String> permissions = Lists.newArrayList();
        roleList.forEach( i -> roles.add(i.getRoleName()));
        permissionList.forEach( i -> permissions.add(i.getPermission()));

        user.setRoles(roles).setPermissions(permissions);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                user.getRealname());

        return authenticationInfo;
    }

}
