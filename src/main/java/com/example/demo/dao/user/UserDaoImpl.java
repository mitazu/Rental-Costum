package com.example.demo.dao.user;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.model.UserRequest;
import com.example.demo.util.Helpers;
import com.example.demo.util.jwt.SessionUtil;
import com.example.demo.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class UserDaoImpl extends DBQueryHandler implements UserDao {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserDaoImpl(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.setDataSource(dataSource);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";



    @Override
    public ResponseEntity<?> insertUser(UserRequest userRequest, HttpServletRequest request) {
        Connection con = Connect();
        log.info(log_template_enter, "insertUser", new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
        try {
            userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            Object[] obj = {
                    userRequest.getNama_lengkap(),
                    userRequest.getTelepon(),
                    userRequest.getMail(),
                    userRequest.getAlamat(),
                    userRequest.getFoto(),
                    userRequest.getFoto_ktp(),
                    userRequest.getUsername(),
                    userRequest.getPassword(),
                    userRequest.getUser_active(),
                    userRequest.getRole_id(),
                    SessionUtil.getUserData(request).getUser_name()
            };
            String result = CallFunctionRetString(con, "user_func_insert", obj);
            if(!result.equals("USER BERHASIL DIBUAT")){
                log.error(log_template_error, "insertUser", result, new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
                throw new RuntimeException(result);
            }
            Commit(con);
            log.info(log_template_response, "insertUser", new java.util.Date(), request.getRemoteAddr(), result);
            return Response.response(result, HttpStatus.OK);
        } catch (RuntimeException e){
            Rollback(con);
            log.error(log_template_error, "insertUser", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> getDataUser(String user_id, String nama, HttpServletRequest request){
        Connection con =Connect();
        try {
            Object[] obj = {
                    user_id,
                    nama
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con, "user_getdatauser", obj, new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }

    }
    @Override
    public ResponseEntity<?> updatePassword(String password,HttpServletRequest request){
        Connection con =Connect();
        try {
            String pass = bCryptPasswordEncoder.encode(password);

            Object[] obj = {
                    pass,
                    SessionUtil.getUserData(request).getUser_name()
            };
            String msg = CallFunctionRetString(con, "user_updatepassword", obj);
            if (!msg.equals("Success")) {
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Password berhasil diubah", HttpStatus.OK);
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }

}
