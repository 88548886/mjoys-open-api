package com.mjoys.auth.jwt;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authc.AuthenticationToken;


@Data
@Accessors(chain = true)
public class JwtToken implements AuthenticationToken {

    private String raw;


    @Override
    public Object getPrincipal() {
        return raw;
    }

    @Override
    public Object getCredentials() {
        return raw;
    }

}