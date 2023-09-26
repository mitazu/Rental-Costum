package com.example.demo.dao.AdminDao;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AdminDao {
    ResponseEntity<?> getDataDashboard(HttpServletRequest request);
}
