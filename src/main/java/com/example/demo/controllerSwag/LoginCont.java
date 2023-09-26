package com.example.demo.controllerSwag;

import com.example.demo.dao.login.LoginDao;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "LOGIN")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
public class LoginCont {
    private final LoginDao loginDao;
    private final AuthenticationManager authenticationManager;
    @Operation(summary = "LOGIN")
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(
            @RequestBody LoginRequest loginRequest,
             HttpServletRequest request
    ){
          return loginDao.doLogin(loginRequest,request);
    }
    @PostMapping("/register")
    public ResponseEntity<?> doRegister(
            @RequestBody RegisterRequest registerRequest,
            HttpServletRequest request
    ){
        return loginDao.doRegister(registerRequest, request);
    }

    @Operation(summary = "SESSION END")
    @PostMapping("/logout")
    public ResponseEntity<?> doLogout(
            HttpServletRequest request
    ) {
        return loginDao.doLogout(request);
    }
}
