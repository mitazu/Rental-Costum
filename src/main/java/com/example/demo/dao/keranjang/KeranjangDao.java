package com.example.demo.dao.keranjang;

import com.example.demo.model.KeranjangRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface KeranjangDao {
    ResponseEntity<?> getDataKeranjang(String user_id, String keranjang_id);
    ResponseEntity<?> setDataKeranjang(KeranjangRequest keranjangRequest, HttpServletRequest request);
    ResponseEntity<?> deleteDataKeranjang(String keranjang_id);
}
