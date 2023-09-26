package com.example.demo.controllerSwag;


import com.example.demo.dao.HistoryDao.HistoryDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "HISTORY")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryCont {
    private final HistoryDao historyDao;

    @Operation(summary = "GET HISTORY")
    @GetMapping("/getDataHistory")
    public ResponseEntity<?> getHistory(@RequestParam(required = false,defaultValue = "") String user_id,
                                        @RequestParam(required = false,defaultValue = "") String pesanan_id,
                                        HttpServletRequest request){
        return historyDao.getDataHistory(user_id,pesanan_id,request);
    }


}
