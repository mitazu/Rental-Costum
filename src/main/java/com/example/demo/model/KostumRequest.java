package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class KostumRequest {
    private String kostum_id;
    private String kostum_name;
    private Integer kategori_id;
    private String size_kostum;
    private String deskripsi_kostum;
    private String foto_kostum;
    private Integer harga_kostum;
}
