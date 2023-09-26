package com.example.demo.dao.HistoryDao;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface HistoryDao {
    ResponseEntity<?> getDataHistory (String user_id, String pesanan_id, HttpServletRequest request);
}
