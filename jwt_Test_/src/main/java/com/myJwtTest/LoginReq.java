package com.myJwtTest;

import lombok.Data;

@Data
public class LoginReq {

    private String username;
    private String password;
}