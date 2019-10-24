package com.mjoys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private int id;
    private String username;
    private String realname;
    private String password;
    private String salt;
    private List<String> roles;
    private List<String> permissions;
}
