package com.example.demo.dao.pesanan;

import com.example.demo.model.PesananRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PesananDao {
    ResponseEntity<?> getDataPesanan(String user_id, String pesanan_id, HttpServletRequest request);

    ResponseEntity<?> cekKetersediaan(String user_id, String kostum_id, String tanggal_ambil, String tanggal_kembali, HttpServletRequest request);

    ResponseEntity<?> insertPesanan(PesananRequest pesananRequest, HttpServletRequest request);

    ResponseEntity<?> batalPesanan(String pesanan_id);

    ResponseEntity<?> approvePesanan(String pesanan_id, HttpServletRequest req);

    ResponseEntity<?> disapprovePesanan(String pesanan_id, HttpServletRequest req);

    ResponseEntity<?> getProvinsi();

    ResponseEntity<?> getKota(String provinsi_id, HttpServletRequest request);

    ResponseEntity<?> cekOngkir(String kota_id, String kurir, HttpServletRequest request);
}
