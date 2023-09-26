package com.example.demo.dao.HistoryDao;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
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
public class HistoryDaoImpl extends DBQueryHandler implements HistoryDao {
    public HistoryDaoImpl(DataSource dataSource){this.setDataSource(dataSource);}

    @Override
    public ResponseEntity<?> getDataHistory (String user_id, String pesanan_id, HttpServletRequest request){
        Connection con = Connect();
        try {
            Object[] obj ={
                    user_id,
                    pesanan_id
            };
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"history_getdatahistory",obj,new Mapper());

            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }
}
