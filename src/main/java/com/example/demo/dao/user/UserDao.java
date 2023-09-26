package com.example.demo.dao.user;

import com.example.demo.model.UserRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserDao {
    ResponseEntity<?> insertUser(UserRequest userRequest, HttpServletRequest request);
    ResponseEntity<?> getDataUser(String user_id, String nama, HttpServletRequest request);
    ResponseEntity<?> updatePassword(String password,HttpServletRequest request);
}
