package com.example.demo.model;

import lombok.Data;

@Data
public class KeranjangRequest {
    private String keranjang_id;
    private String user_id;
    private String costum_id;
    private String tanggal_ambil;
    private String tanggal_kembali;
}
