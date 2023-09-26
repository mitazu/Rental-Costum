package com.example.demo.controllerSwag;

import com.example.demo.dao.AdminDao.AdminDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Admin")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCont {
    private final AdminDao adminDao;

    @Operation(summary = "GET DATA DAHSBOARD")
    @GetMapping("/getDataDashboard")
    public ResponseEntity<?> getDataDashboard(HttpServletRequest request){
        return adminDao.getDataDashboard(request);
    }
}
