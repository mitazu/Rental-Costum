package com.example.demo.dao.dao;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class DaoImpl extends DBQueryHandler implements Dao {


    public DaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }
    @Override
    public ResponseEntity<?> get(HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {};
//          Responnya berupa list object
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con, "", obj, new Mapper());

//          Responnya hanya 1 object
            LinkedHashMap<String, String> linkedHashMap = ExecuteSingleCallPostgre(con, "", obj, new Mapper());

            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> post(HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {};
            String msg = CallFunctionRetString(con, "", obj);
            if(!msg.equals("Success")){
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response(msg, HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> update(HttpServletRequest request) {
        try {

            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(HttpServletRequest request) {
        try {

            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
