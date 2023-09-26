package com.example.demo.dao.login;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.util.jwt.JwtTokenResponse;
import com.example.demo.util.jwt.JwtTokenUtil;
import com.example.demo.util.jwt.SessionUtil;
import com.example.demo.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.LinkedHashMap;

import static com.example.demo.util.Helpers.getString;

@Slf4j
@Service
@Transactional
public class LoginDaoImpl extends DBQueryHandler implements LoginDao {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    LoginDaoImpl(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setDataSource(dataSource);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ResponseEntity<?> doLogin(LoginRequest loginRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUser_name(), loginRequest.getUser_password()));
            }catch(RuntimeException e){
                throw new RuntimeException("User tidak ditemukan");
            }
            Object[] obj = {loginRequest.getUser_name()};
            LinkedHashMap<String, String> linkedHashMap = ExecuteSingleCallPostgre(con, "user_func_login", obj, new Mapper());
            linkedHashMap.remove("user_password");

            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
            jwtTokenResponse.setUser_id(Integer.parseInt(getString(linkedHashMap.get("user_id"))));
            jwtTokenResponse.setUser_name(getString(linkedHashMap.get("user_name")));
            jwtTokenResponse.setUser_role(getString(linkedHashMap.get("role_id")));
            jwtTokenResponse.setEmail(getString(linkedHashMap.get("email")));

            String token = jwtTokenUtil.generateToken(jwtTokenResponse);
            linkedHashMap.put("token", token);

            Object[] objToken = {linkedHashMap.get("user_name"), request, token};
            String resultToken = CallFunctionRetString(con, "user_func_insert_token", objToken);
            if (!resultToken.equals("Success")) {
                throw new RuntimeException(resultToken);
            }
            Commit(con);
            return Response.response(linkedHashMap, "Login Berhasil", HttpStatus.OK);
        } catch (RuntimeException e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> doRegister(RegisterRequest registerRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            String password = bCryptPasswordEncoder.encode(registerRequest.getPassword());

            Object[] obj = {
                    registerRequest.getNama_lengkap(),
                    registerRequest.getTelepon(),
                    registerRequest.getMail(),
                    registerRequest.getAlamat(),
                    registerRequest.getFoto(),
                    registerRequest.getFoto_ktp(),
                    registerRequest.getUsername(),
                    password
            };
            String result = CallFunctionRetString(con, "user_func_register", obj);
            if (!result.equals("USER BERHASIL DIBUAT")) {
                throw new RuntimeException(result);
            }
            Commit(con);
            return Response.response("User dengan email " + registerRequest.getMail() + " berhasil dibuat", HttpStatus.OK);
        } catch (RuntimeException e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> doLogout(HttpServletRequest request) {
        Connection con = Connect();
        try {
            JwtTokenResponse response = SessionUtil.getUserData(request);
            String token = SessionUtil.getUserToken(request);

            Object[] obj = {response.getUser_name(), token};
            String result = CallFunctionRetString(con, "user_func_logout_token", obj);
            if (!result.equals("Success")) {
                throw new RuntimeException(result);
            }
            Commit(con);
            return Response.response("User " + response.getUser_name() + " berhasil logout", HttpStatus.OK);
        } catch (RuntimeException e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
    @Override
    public LinkedHashMap<String, String> doLogin2(LoginRequest loginRequest) {
        Connection con = Connect();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        try {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUser_name(), loginRequest.getUser_password()));
            }catch(RuntimeException e){
                throw new RuntimeException("User tidak ditemukan");
            }

            Object[] obj = {loginRequest.getUser_name()};
            linkedHashMap = ExecuteSingleCallPostgre(con, "user_func_login", obj, new Mapper());
            linkedHashMap.remove("user_password");

            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
            jwtTokenResponse.setUser_id(Integer.parseInt(getString(linkedHashMap.get("user_id"))));
            jwtTokenResponse.setUser_name(getString(linkedHashMap.get("user_name")));
            jwtTokenResponse.setUser_role(getString(linkedHashMap.get("role_id")));
            jwtTokenResponse.setEmail(getString(linkedHashMap.get("email")));

            String token = jwtTokenUtil.generateToken(jwtTokenResponse);
            linkedHashMap.put("token", token);

            Object[] objToken = {linkedHashMap.get("user_name"), "", token};
            String resultToken = CallFunctionRetString(con, "user_func_insert_token", objToken);
            if (!resultToken.equals("Success")) {
                throw new RuntimeException(resultToken);
            }
            linkedHashMap.put("message", resultToken);
            Commit(con);
            return  linkedHashMap;
        } catch (RuntimeException e) {
            Rollback(con);
            linkedHashMap.put("message", e.getMessage());
            return linkedHashMap;
        } finally {
            Close(con);
        }
    }
    @Override
    public String doRegister2(RegisterRequest registerRequest) {
        Connection con = Connect();
        try {
            String password = bCryptPasswordEncoder.encode(registerRequest.getPassword());
            registerRequest.setAlamat("null");
            registerRequest.setFoto_ktp("null");
            Object[] obj = {
                    registerRequest.getNama_lengkap(),
                    registerRequest.getTelepon(),
                    registerRequest.getMail(),
                    registerRequest.getAlamat(),
                    registerRequest.getFoto(),
                    registerRequest.getFoto_ktp(),
                    registerRequest.getUsername(),
                    password
            };
            String result = CallFunctionRetString(con, "user_func_register", obj);
            if (!result.equals("USER BERHASIL DIBUAT")) {
                throw new RuntimeException("||"+result);
            }
            Commit(con);
            return (registerRequest.getUsername() + "|| Success");
        } catch (RuntimeException e) {
            Rollback(con);
            return e.getMessage();
        } finally {
            Close(con);
        }
    }

}
