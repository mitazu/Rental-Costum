package com.example.demo.controllerSwag;

import com.example.demo.dao.pesanan.PesananDao;
import com.example.demo.model.PesananRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Pesanan")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/pesanan")
@RequiredArgsConstructor
public class PesananCont {
    private final PesananDao pesananDao;

    @Operation(summary = "GET DATA PESANAN")
    @GetMapping("/getDataPesanan")
    public ResponseEntity<?> getDataPesanan(@RequestParam (required = false,defaultValue = "") String user_id,
                                      @RequestParam (required = false,defaultValue = "") String pesanan_id,
                                      HttpServletRequest request){
        return pesananDao.getDataPesanan(user_id,pesanan_id,request);
    }
    @Operation(summary = "SET DATA PESANAN")
    @PostMapping("/setDataPesanan")
    public ResponseEntity<?> setDataPesanan(@RequestBody PesananRequest pesananRequest,
                                           HttpServletRequest request){
        return pesananDao.insertPesanan(pesananRequest,request);
    }
    @Operation(summary = "CEK KETERSEDIAAN KOSTUM")
    @GetMapping("/cekKetersediaanKostum")
    public ResponseEntity<?> cekKetersediaanKostum(
            @RequestParam (required = false,defaultValue = "") String user_id,
             @RequestParam (required = false,defaultValue = "") String costum_id,
             @RequestParam (required = false,defaultValue = "") String tanggal_ambil,
             @RequestParam (required = false,defaultValue = "") String tanggal_kembali,
             HttpServletRequest request
     ){
        return pesananDao.cekKetersediaan(user_id,costum_id,tanggal_ambil,tanggal_kembali,request);
     }
    @Operation(summary = "BATAL PESANAN")
    @PostMapping("/batalPesanan")
    public ResponseEntity<?> batalPesanan(
            @RequestParam (required = false,defaultValue = "") String pesanan_id,
            HttpServletRequest request
    ){
        return pesananDao.batalPesanan(pesanan_id);
    }

    @Operation(summary = "APPROVE PESANAN")
    @PostMapping("/approvePesanan")
    public ResponseEntity<?> approvePesanan(
            @RequestParam String pesanan_id,
            HttpServletRequest request
    ){
        return pesananDao.approvePesanan(pesanan_id,request);
    }
    @Operation(summary = "DISAPPROVE PESANAN")
    @PostMapping("/disapprovePesanan")
    public ResponseEntity<?> disapprovePesanan(
            @RequestParam (required = false,defaultValue = "") String pesanan_id,
            HttpServletRequest request
    ){
        return pesananDao.disapprovePesanan(pesanan_id,request);
    }
    @Operation(summary = "GET PROVINSI")
    @GetMapping("/getProvinsi")
    public ResponseEntity<?> getProvinsi(){
        return pesananDao.getProvinsi();
    }
    @Operation(summary = "GET KOTA")
    @GetMapping("/getKota")
    public ResponseEntity<?> getKota(
            @RequestParam (required = false,defaultValue = "") String provinsi_id,
            HttpServletRequest request)
    {
        return pesananDao.getKota(provinsi_id,request);
    }
    @Operation(summary = "CEK ONGKIR")
    @GetMapping("/cekOngkir")
    public ResponseEntity<?> cekOngkir(
            @RequestParam (required = false, defaultValue = "") String kota_id,
            @RequestParam (required = false, defaultValue = "") String kurir,
            HttpServletRequest request
    ){
        return pesananDao.cekOngkir(kota_id,kurir,request);
    }

}
