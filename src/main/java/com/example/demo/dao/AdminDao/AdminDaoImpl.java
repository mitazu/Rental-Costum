package com.example.demo.dao.AdminDao;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.util.jwt.SessionUtil;
import com.example.demo.util.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class AdminDaoImpl extends DBQueryHandler implements AdminDao {
    public AdminDaoImpl (DataSource dataSource){this.setDataSource(dataSource);}
    @Override
    public ResponseEntity<?> getDataDashboard(HttpServletRequest request){
        Connection con = Connect();
        try {
            Object[] obj = {
                    SessionUtil.getUserData(request).getUser_name()
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con, "admin_getDataDashboard", obj, new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }
}
