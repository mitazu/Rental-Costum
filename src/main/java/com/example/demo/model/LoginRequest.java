package com.example.demo.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String user_name;
    private String user_password;
}
