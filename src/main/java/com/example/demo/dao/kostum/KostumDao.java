package com.example.demo.dao.kostum;

import com.example.demo.model.KostumRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;

public interface KostumDao {
    ResponseEntity<?> getKategori(HttpServletRequest request);
    ResponseEntity<?> getDataKostum(String sortby,String costum_name,String kategori_id, String min_price, String max_price, HttpServletRequest request);
    ResponseEntity<?> getDataKostumDetail(String costum_id, HttpServletRequest request);
    ResponseEntity<?> setDataKostum(KostumRequest kostumRequest, HttpServletRequest request);
    ResponseEntity<?> deleteDataKostum (String costum_id,HttpServletRequest request);
    List<LinkedHashMap<String, String>> getDataKostum2(String sortby, String costum_name, String kategori_id, String min_price, String max_price);
    List<LinkedHashMap<String,String>> getDataKostumDetail2 (String id);
}
