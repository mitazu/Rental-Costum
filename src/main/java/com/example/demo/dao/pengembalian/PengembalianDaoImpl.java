package com.example.demo.dao.pengembalian;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.model.PengembalianRequest;
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
public class PengembalianDaoImpl extends DBQueryHandler implements PengembalianDao {
    public PengembalianDaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }
    @Override
    public ResponseEntity<?> getDataPengembalianforKirim (String pesanan_id,HttpServletRequest request){
        Connection con = Connect();
        try {
            Object[] obj ={
                    pesanan_id
            };
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"pengembalian_getpengembalianforkirim",obj,new Mapper());

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
    public ResponseEntity<?> getDataPengembalian (String user_id, String pengembalian_id, HttpServletRequest request){
        Connection con = Connect();
        try {
            Object[] obj ={
                    user_id,
                    pengembalian_id
            };
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"pengembalian_getpengembalian",obj,new Mapper());

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
    public ResponseEntity<?> setDataPengembalian (PengembalianRequest pengembalianRequest, HttpServletRequest request){
        Connection con = Connect();
        try {
            if(pengembalianRequest.getPengembalian_id().equals("0")){
                String id = CallFunctionRetString(con,"pengembalian_getpengembalianid", new Object[]{});
                Object[] obj ={
                    Integer.parseInt(id),
                    pengembalianRequest.getPesanan_id(),
                    pengembalianRequest.getUser_id(),
                    pengembalianRequest.getTanggal_pengembalian(),
                    pengembalianRequest.getKeterlambatan(),
                    pengembalianRequest.getDenda(),
                    pengembalianRequest.getBukti_pembayaran()
                };
                String msg = CallFunctionRetString(con,"pengembalian_setpengembalian",obj);
                if(!msg.equals("Success")){
                    throw new RuntimeException(msg);
                }
                Commit(con);
                return Response.response("Data Pengembalian berhasil ditambah", HttpStatus.OK);
            }else{
                Object[] obj ={
                        pengembalianRequest.getPengembalian_id(),
                        pengembalianRequest.getPesanan_id(),
                        pengembalianRequest.getUser_id(),
                        pengembalianRequest.getTanggal_pengembalian(),
                        pengembalianRequest.getKeterlambatan(),
                        pengembalianRequest.getDenda(),
                        pengembalianRequest.getBukti_pembayaran()
                };
                String msg = CallFunctionRetString(con,"pengembalian_updatepengembalian",obj);
                if(!msg.equals("Success")){
                    throw new RuntimeException(msg);
                }
                Commit(con);
                return Response.response("Data Pengembalian berhasil diupdate", HttpStatus.OK);
            }
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> approvePengembalian (String pengembalian_id){
        Connection con = Connect();
        try {
            String msg = CallFunctionRetString(con,"pengembalian_approvepengembalian",new Object[]{});
            if(!msg.equals("Success")){
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Pengembalian berhasil disetujui",HttpStatus.OK);
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> disapprovePengembalian (String pengembalian_id){
        Connection con = Connect();
        try {
            String msg = CallFunctionRetString(con,"pengembalian_disapprovepengembalian",new Object[]{});
            if(!msg.equals("Success")){
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Pengembalian berhasil ditolak",HttpStatus.OK);
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }

}
