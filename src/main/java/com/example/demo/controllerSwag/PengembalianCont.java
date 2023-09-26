package com.example.demo.controllerSwag;

import com.example.demo.dao.pengembalian.PengembalianDao;
import com.example.demo.model.PengembalianRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "PENGEMBALIAN")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/pengembalian")
@RequiredArgsConstructor
public class PengembalianCont {
    private final PengembalianDao pengembalianDao;

    @Operation(summary = "GET DATA PENGEMBALIAN FOR KIRIM")
    @GetMapping("/getDataPengembalianforKirim")
    public ResponseEntity<?> getDataPengembalianforKirim(@RequestParam (required = false,defaultValue = "") String pesanan_id,
                                                 HttpServletRequest request){
        return pengembalianDao.getDataPengembalianforKirim(pesanan_id,request);
    }
    @Operation(summary = "GET DATA PENGEMBALIAN")
    @GetMapping("/getDataPengembalian")
    public ResponseEntity<?> getDataPengembalian(@RequestParam (required = false,defaultValue = "") String user_id,
                                                 @RequestParam (required = false,defaultValue = "") String pengembalian_id,
                                                 HttpServletRequest request){
        return pengembalianDao.getDataPengembalian(user_id,pengembalian_id,request);
    }
    @Operation(summary = "SET DATA PENGEMBALIAN")
    @PostMapping("/setDataPengembalian")
    public ResponseEntity<?> setDataPengembalian(@RequestBody PengembalianRequest pengembalianRequest,
                                                 HttpServletRequest request){
        return pengembalianDao.setDataPengembalian(pengembalianRequest,request);
    }

}
