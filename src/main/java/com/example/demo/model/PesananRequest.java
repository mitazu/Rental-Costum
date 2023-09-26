package com.example.demo.model;

import lombok.Data;

@Data
public class PesananRequest {
    private String pesanan_id;
    private String user_id;
    private String costum_id;
    private String tanggal_ambil;
    private String tanggal_kembali;
    private String opsi_pengiriman;
    private String ongkos_kirim;
    private String total_bayar;
    private String bukti_pembayaran;
}
