package com.example.demo.dao.IntegDao;

import com.example.demo.model.LoginRequest;
import org.json.simple.JSONObject;

public interface IntegDao {
    JSONObject cekOngkir(LoginRequest loginRequest);
}
