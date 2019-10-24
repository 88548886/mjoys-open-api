package com.mjoys.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.common.collect.Lists;
import com.mjoys.entity.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class JwtUtil {

    public static String issue(int userId, String username, List<String> roles, List<String> permissions) {

        Algorithm algorithm = Algorithm.HMAC256("mjoys.com");
        JWTCreator.Builder builder = JWT.create()
                .withJWTId(String.valueOf(System.currentTimeMillis()))
                .withAudience(username)
                .withIssuer("mjoys.com")
                .withIssuedAt(DateTime.now().toDate())
                .withSubject(username)
                .withNotBefore(DateTime.now().toDate())
                .withExpiresAt(DateTime.now().plusDays(30).toDate())
                .withClaim("uid", userId)
                .withClaim("username", username)
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withArrayClaim("permissions", permissions.toArray(new String[permissions.size()]));
        return builder.sign(algorithm);
    }


    public static boolean verify(String token, String secret) {
        //验证jwt签名，有可能的情况如下
        //过期
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("mjoys.com").build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static User parse(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> claims = jwt.getClaims();
        int userId = claims.get("uid").asInt();
        String username = claims.get("username").asString();
        String[] rolesArr = claims.get("roles").asArray(String.class);
        String[] permissionsArr = claims.get("permissions").asArray(String.class);

        List<String> roles = Lists.newArrayList();
        List<String> permissions = Lists.newArrayList();
        for (int i = 0; i < rolesArr.length; i++) {
            roles.add(rolesArr[i]);
        }
        for (int i = 0; i < permissionsArr.length; i++) {
            permissions.add(permissionsArr[i]);
        }
        User user = new User().setId(userId).setUsername(username).setRoles(roles).setPermissions(permissions);
        return user;
    }
}
