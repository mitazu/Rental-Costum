package com.example.demo.controllerSwag;

import com.example.demo.dao.keranjang.KeranjangDao;
import com.example.demo.model.KeranjangRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Keranjang")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/keranjang")
@RequiredArgsConstructor
public class KeranjangCont {
    private final KeranjangDao keranjangDao;
    @Operation(summary = "GET DATA KERANJANG")
    @GetMapping("/getDataKeranjang")
    public ResponseEntity<?> getDataKeranjang(@RequestParam (required = false,defaultValue = "") String user_id,
                                              @RequestParam (required = false,defaultValue = "") String keranjang_id,
                                              HttpServletRequest request){
        return keranjangDao.getDataKeranjang(user_id,keranjang_id);
    }
    @Operation(summary = "SET DATA KERANJANG")
    @PostMapping("/setDataKeranjang")
    public ResponseEntity<?> setDataKeranjang(@RequestBody KeranjangRequest keranjangRequest,
                                            HttpServletRequest request){
        return keranjangDao.setDataKeranjang(keranjangRequest,request);
    }
    @Operation(summary = "DELETE DATA KERANJANG")
    @PostMapping("/deleteDataKeranjang")
    public ResponseEntity<?> setDataKeranjang(@RequestParam String keranjang_id,
                                              HttpServletRequest request){
        return keranjangDao.deleteDataKeranjang(keranjang_id);
    }

}
