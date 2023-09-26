package com.example.demo.dao.IntegDao;

import com.example.demo.api.IntegrasiApi;
import com.example.demo.model.LoginRequest;
import com.example.demo.api.IntegrasiApi;
import com.example.demo.model.LoginRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Component
public class IntegDaoImpl implements IntegDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final IntegrasiApi api;

    public IntegDaoImpl(@Qualifier("INTEGRASI_HTTP_CLIENT") Retrofit retrofit){
        this.api = retrofit.create(IntegrasiApi.class);
    }

    @Override
    public JSONObject cekOngkir(LoginRequest loginRequest) {
        AtomicReference<JSONObject> resp = new AtomicReference<>();
        api.cekOngkir(loginRequest)
                .doOnError(Throwable::printStackTrace)
                .doOnSuccess(resp::set)
                .subscribe();
        return resp.get();
    }
}
