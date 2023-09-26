package com.example.demo.model;
import lombok.Data;

@Data
public class PengembalianRequest {
    private String pengembalian_id;
    private String pesanan_id;
    private String user_id;
    private String tanggal_pengembalian;
    private String keterlambatan;
    private String denda;
    private String bukti_pembayaran;
}
