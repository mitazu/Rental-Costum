package com.example.demo.controllerSwag;

import com.example.demo.dao.user.UserDao;
import com.example.demo.model.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "USER")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserCont {

    private final UserDao userDao;
    @Operation(summary = "CREATE USER")
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(
            @RequestBody UserRequest userRequest,
            HttpServletRequest request
    ){
        return userDao.insertUser(userRequest, request);
    }
    @Operation(summary = "GET DATA USER")
    @PostMapping("/getDataUser")
    public ResponseEntity<?> getDataUser(
            @RequestParam (required = false,defaultValue = "") String user_id,
            @RequestParam (required = false,defaultValue = "") String nama,
            HttpServletRequest request
    ){
        return userDao.getDataUser(user_id,nama,request);
    }
    @Operation(summary = "UPDATE PASSWORD")
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(
            @RequestParam (required = false,defaultValue = "") String password,
            HttpServletRequest request
    ){
        return userDao.updatePassword(password,request);
    }
}
