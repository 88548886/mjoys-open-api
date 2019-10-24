package com.mjoys.auth.shiro;

import com.mjoys.auth.jwt.JwtToken;
import com.mjoys.auth.jwt.JwtUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class JwtCredentialsMatcher implements CredentialsMatcher {


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        return JwtUtil.verify(jwtToken.getRaw(), "mjoys.com");
    }
}
