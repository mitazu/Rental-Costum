package com.example.demo.api;

import com.example.demo.model.LoginRequest;
import io.reactivex.Single;
import org.json.simple.JSONObject;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface IntegrasiApi {
    @GET("cekOngkir")
    Single<JSONObject> cekOngkir(
        @Body LoginRequest loginRequest
    );

}


