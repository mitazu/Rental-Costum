package com.example.demo.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nama_lengkap;
    private String telepon;
    private String mail;
    private String alamat;
    private String foto;
    private String foto_ktp;
    private String username;
    private String password;
}

