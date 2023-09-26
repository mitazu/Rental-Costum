package com.example.demo.dao.pengembalian;

import com.example.demo.model.PengembalianRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PengembalianDao {
    ResponseEntity<?> getDataPengembalianforKirim (String pesanan_id,HttpServletRequest request);
    ResponseEntity<?> getDataPengembalian (String user_id, String pengembalian_id,HttpServletRequest request);
    ResponseEntity<?> setDataPengembalian (PengembalianRequest pengembalianRequest, HttpServletRequest request);
    ResponseEntity<?> approvePengembalian (String pengembalian_id);
    ResponseEntity<?> disapprovePengembalian (String pengembalian_id);
}
