package com.example.demo.dao.kostum;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.model.KostumRequest;
import com.example.demo.util.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class KostumDaoImpl extends DBQueryHandler implements KostumDao {
    public KostumDaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    @Override
    public ResponseEntity<?> getKategori(HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {};
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"kostum_getkategori",obj,new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> getDataKostum(String sortby,String costum_name,String kategori_id, String min_price, String max_price, HttpServletRequest request){
        Connection con = Connect();
        try {
            Object[] obj = {
                    sortby,
                    costum_name,
                    kategori_id,
                    min_price,
                    max_price
            };
            System.out.println(costum_name);
            System.out.println(kategori_id);
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"kostum_getDataKostum",obj,new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }
    @Override
    public ResponseEntity<?> getDataKostumDetail(String costum_id, HttpServletRequest request){
        Connection con = Connect();
        try{
            Object[] obj = {
                    costum_id
            };
            List<LinkedHashMap<String,String>> linkedHashMaps = ExecuteCallPostgre(con,"kostum_getDataKostumDetail",obj,new Mapper());
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
    public ResponseEntity<?> setDataKostum(KostumRequest kostumRequest, HttpServletRequest request){
        Connection con =Connect();
        try{
            if(kostumRequest.getKostum_id().equals("0")){
                String id = CallFunctionRetString(con,"kostum_getkostumid",new Object[]{});
                Object[] obj2 = {
                        Integer.parseInt(id),
                        kostumRequest.getKostum_name(),
                        kostumRequest.getKategori_id(),
                        kostumRequest.getSize_kostum(),
                        kostumRequest.getDeskripsi_kostum(),
                        kostumRequest.getFoto_kostum(),
                        kostumRequest.getHarga_kostum()
                };
                String msg2 = CallFunctionRetString(con,"kostum_setdatakostum",obj2);
                if(!msg2.equals("Success")){
                    throw new RuntimeException(msg2);
                }
                Commit(con);
                return Response.response("Data Kostum berhasil ditambah", HttpStatus.OK);
            }else{
                Object[] obj2 = {
                        kostumRequest.getKostum_id(),
                        kostumRequest.getKostum_name(),
                        kostumRequest.getKategori_id(),
                        kostumRequest.getSize_kostum(),
                        kostumRequest.getDeskripsi_kostum(),
                        kostumRequest.getFoto_kostum(),
                        kostumRequest.getHarga_kostum()
                };
                String msg2 = CallFunctionRetString(con,"kostum_updatedatakostum",obj2);
                if(!msg2.equals("Success")){
                    throw new RuntimeException(msg2);
                }
                Commit(con);
                return Response.response("Data Kostum berhasil diupdate", HttpStatus.OK);
            }
        }catch(Exception e){
            Rollback(con);
            return Response.response(e.getMessage(),HttpStatus.BAD_REQUEST);
        }finally{
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> deleteDataKostum (String costum_id,HttpServletRequest request){
        Connection con = Connect();
        try {
            Object[] obj = {
                    costum_id
            } ;
            String msg = CallFunctionRetString(con,"kostum_deletedatakostum",obj);
            if(!msg.equals("Success")) {
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Data Kostum berhasil dihapus", HttpStatus.OK);
        }catch (Exception e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }finally {
            Close(con);
        }
    }

    @Override
    public List<LinkedHashMap<String, String>> getDataKostum2(String sortby, String costum_name, String kategori_id, String min_price, String max_price){
        Connection con = Connect();
        List<LinkedHashMap<String,String>> linkedHashMaps = new ArrayList<>();
        try {
            Object[] obj ={
                sortby,
                costum_name,
                kategori_id,
                min_price,
                max_price
            };
            System.out.println(costum_name);
            System.out.println(kategori_id);
            linkedHashMaps = ExecuteCallPostgre(con,"kostum_getDataKostum",obj,new Mapper());
            Commit(con);
            return linkedHashMaps;
        }catch (Exception e){
            Rollback(con);
            LinkedHashMap<String, String> errorMap = new LinkedHashMap<>();
            errorMap.put("message", e.getMessage());
            linkedHashMaps.add(errorMap);
            return linkedHashMaps;
        }finally {
            Close(con);
        }
    }
    @Override
    public List<LinkedHashMap<String,String>>  getDataKostumDetail2(String costum_id){
        Connection con = Connect();
        List<LinkedHashMap<String,String>> linkedHashMaps = new ArrayList<>();
        try{
            Object[] obj = {
                    costum_id
            };
            linkedHashMaps = ExecuteCallPostgre(con,"kostum_getDataKostumDetail",obj,new Mapper());
            Commit(con);
            return linkedHashMaps;
        }catch (Exception e){
            Rollback(con);
            LinkedHashMap<String, String> errorMap = new LinkedHashMap<>();
            errorMap.put("message", e.getMessage());
            linkedHashMaps.add(errorMap);
            return linkedHashMaps;
        }finally {
            Close(con);
        }
    }

//    @Override
//    public ResponseEntity<?> setDataKostum(KostumRequest kostumRequest,HttpServletRequest request){
//        Connection con =Connect();
//        try{
//            if(kostumRequest.getKostum_id() == 0){
//                String id = CallFunctionRetString(con,"kostum_getkostumid",new Object[]{});
//                Object[] obj2 = {
//                        Integer.parseInt(id),
//                        kostumRequest.getKostum_name(),
//                        kostumRequest.getKategori_id(),
//                        kostumRequest.getDeskripsi_kostum(),
//                        kostumRequest.getFoto_kostum(),
//                        kostumRequest.getHarga_kostum()
//                };
//                String msg2 = CallFunctionRetString(con,"kostum_setdatakostum",obj2);
//                if(!msg2.equals("Success")){
//                    throw new RuntimeException(msg2);
//                }
//                for(KostumDetailRequest e : kostumRequest.getKostumDetailRequests()){
//                    Object[] obj = {
//                            Integer.parseInt(id),
//                            e.getProperti_name(),
//                            e.getHarga_properti(),
//                            e.getFoto_properti()
//                    };
//                    String msg = CallFunctionRetString(con,"kostum_setdatakostumdetail", obj);
//                    if(!msg.equals("Success")) {
//                        throw new RuntimeException(msg);
//                    }
//                }
//                Commit(con);
//                return Response.response("Data Kostum berhasil ditambah", HttpStatus.OK);
//            }else{
//                Object[] obj2 = {
//                        kostumRequest.getKostum_id(),
//                        kostumRequest.getKostum_name(),
//                        kostumRequest.getKategori_id(),
//                        kostumRequest.getDeskripsi_kostum(),
//                        kostumRequest.getFoto_kostum(),
//                        kostumRequest.getHarga_kostum()
//                };
//                String msg2 = CallFunctionRetString(con,"kostum_updatedatakostum",obj2);
//                if(!msg2.equals("Success")){
//                    throw new RuntimeException(msg2);
//                }
//                for(KostumDetailRequest e : kostumRequest.getKostumDetailRequests()){
//                    Object[] obj = {
//                            kostumRequest.getKostum_id(),
//                            e.getProperti_name(),
//                            e.getHarga_properti(),
//                            e.getFoto_properti()
//                    };
//                    String msg = CallFunctionRetString(con,"kostum_setdatakostumdetail", obj);
//                    if(!msg.equals("Success")) {
//                        throw new RuntimeException(msg);
//                    }
//                }
//                Commit(con);
//                return Response.response("Data Kostum berhasil diupdate", HttpStatus.OK);
//            }
//        }catch(Exception e){
//            Rollback(con);
//            return Response.response(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }finally{
//            Close(con);
//        }
//    }

}
