package com.example.demo.dao.keranjang;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.model.KeranjangRequest;
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
public class KeranjangDaoImpl extends DBQueryHandler implements KeranjangDao {
    public KeranjangDaoImpl(DataSource dataSource){this.setDataSource(dataSource);}
    @Override
    public ResponseEntity<?> getDataKeranjang(String user_id, String keranjang_id){
        Connection con = Connect();
        try {
            Object[] obj ={
                    user_id,
                    keranjang_id
            };
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"keranjang_getdatakeranjang",obj,new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> setDataKeranjang(KeranjangRequest keranjangRequest, HttpServletRequest request){
        Connection con = Connect();
        try {
            if (keranjangRequest.getKeranjang_id().equals("0")) {
                String id = CallFunctionRetString(con, "keranjang_getkeranjangid", new Object[]{});
                Object[] obj = {
                        Integer.parseInt(id),
                        keranjangRequest.getUser_id(),
                        keranjangRequest.getCostum_id(),
                        keranjangRequest.getTanggal_ambil(),
                        keranjangRequest.getTanggal_kembali()
                };
                String msg = CallFunctionRetString(con, "keranjang_setdatakeranjang", obj);
                if (!msg.equals("Success")) {
                    throw new RuntimeException(msg);
                }
                Commit(con);
                return Response.response( "Berhasil ditambah ke keranjang", HttpStatus.OK);
            } else {
                Object[] obj = {
                        keranjangRequest.getKeranjang_id(),
                        keranjangRequest.getUser_id(),
                        keranjangRequest.getCostum_id(),
                        keranjangRequest.getTanggal_ambil(),
                        keranjangRequest.getTanggal_kembali()
                };
                String msg = CallFunctionRetString(con, "keranjang_updatedatakeranjang", obj);
                if (!msg.equals("Success")) {
                    throw new RuntimeException(msg);
                }
                Commit(con);
                return Response.response("Berhasil update data keranjang", HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
    public ResponseEntity<?> deleteDataKeranjang(String keranjang_id){
        Connection con = Connect();
        try {
            String msg = CallFunctionRetString(con, "keranjang_deletedatakeranjang", new Object[]{keranjang_id});
            if (!msg.equals("Success")) {
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Berhasil menghapus data keranjang", HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
}
