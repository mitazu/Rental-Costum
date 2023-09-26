package com.example.demo.dao.login;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

public interface LoginDao {
    ResponseEntity<?> doLogin(LoginRequest loginRequest, HttpServletRequest request);
    ResponseEntity<?> doRegister(RegisterRequest registerRequest, HttpServletRequest request);
    ResponseEntity<?> doLogout(HttpServletRequest request);
    LinkedHashMap<String, String>  doLogin2(LoginRequest loginRequest);
    String doRegister2(RegisterRequest registerRequest);
}
