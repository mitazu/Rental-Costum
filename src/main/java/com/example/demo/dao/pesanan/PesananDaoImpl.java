package com.example.demo.dao.pesanan;

import com.example.demo.configuration.DBQueryHandler;
import com.example.demo.configuration.Response;
import com.example.demo.model.PesananRequest;
import com.example.demo.util.jwt.SessionUtil;
import com.example.demo.util.mapper.Mapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class PesananDaoImpl extends DBQueryHandler implements PesananDao {
    public PesananDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public ResponseEntity<?> getDataPesanan(String user_id, String pesanan_id, HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {
                    user_id,
                    pesanan_id
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con, "pesanan_getDataPesanan", obj, new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> cekKetersediaan(String user_id,String kostum_id, String tanggal_ambil, String tanggal_kembali, HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {
                    user_id,
                    kostum_id,
                    tanggal_ambil,
                    tanggal_kembali
            };
            String msg = CallFunctionRetString(con, "pesanan_cekketersediaan", obj);

            Commit(con);
            return Response.response(msg, HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> insertPesanan(PesananRequest pesananRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            if (pesananRequest.getPesanan_id().equals("0")) {
                String id = CallFunctionRetString(con, "pesanan_getpesananid", new Object[]{});
                Object[] obj = {
                        Integer.parseInt(id),
                        pesananRequest.getUser_id(),
                        pesananRequest.getCostum_id(),
                        pesananRequest.getTanggal_ambil(),
                        pesananRequest.getTanggal_kembali(),
                        pesananRequest.getOpsi_pengiriman(),
                        pesananRequest.getOngkos_kirim(),
                        pesananRequest.getTotal_bayar(),
                        pesananRequest.getBukti_pembayaran()
                };
                String msg = CallFunctionRetString(con, "pesanan_setdatapesanan", obj);
                if (!msg.equals("Success")) {
                    throw new RuntimeException(msg);
                }
                Commit(con);
                return Response.response("Data Pesanan berhasil ditambah", HttpStatus.OK);
            } else {
                Object[] obj = {
                        pesananRequest.getPesanan_id(),
                        pesananRequest.getUser_id(),
                        pesananRequest.getCostum_id(),
                        pesananRequest.getTanggal_ambil(),
                        pesananRequest.getTanggal_kembali(),
                        pesananRequest.getOpsi_pengiriman(),
                        pesananRequest.getOngkos_kirim(),
                        pesananRequest.getTotal_bayar(),
                        pesananRequest.getBukti_pembayaran()
                };
                String msg = CallFunctionRetString(con, "pesanan_updatedatapesanan", obj);
                if (!msg.equals("Success")) {
                    throw new RuntimeException(msg);
                }
                Commit(con);
                return Response.response("Data Pesanan berhasil diupdate", HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> batalPesanan(String pesanan_id) {
        Connection con = Connect();
        try {
            String msg = CallFunctionRetString(con, "pesanan_deletedatapesanan", new Object[]{pesanan_id});
            if (!msg.equals("Success")) {
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Pesanan berhasil dibatalkan", HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> approvePesanan(String pesanan_id,HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj={
                    pesanan_id,
                    SessionUtil.getUserData(request).getUser_name()
            };
            String msg = CallFunctionRetString(con, "pesanan_approvepesanan", obj);
            if (!msg.equals("Success")) {
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Pesanan berhasil disetujui", HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> disapprovePesanan(String pesanan_id,HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {
                    pesanan_id,
                    SessionUtil.getUserData(request).getUser_name()
            };
            String msg = CallFunctionRetString(con, "pesanan_disapprovepesanan", obj);
            if (!msg.equals("Success")) {
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response("Pesanan berhasil ditolak", HttpStatus.OK);
        } catch (Exception e) {
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> getProvinsi() {
        try {
            OkHttpClient client = new OkHttpClient();

            Request req = new Request.Builder()
                    .url("https://api.rajaongkir.com/starter/province?id=")
                    .get()
                    .addHeader("key", "efe7926901e330a48efcb6f419b471a5")
                    .build();

            JSONObject json = (JSONObject) new JSONParser().parse(client.newCall(req).execute().body().string());
            return Response.response(json, HttpStatus.OK);
        } catch (Exception e) {
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getKota(String provinsi_id, HttpServletRequest request) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request req = new Request.Builder()
                    .url("https://api.rajaongkir.com/starter/city?id=&province="+provinsi_id)
                    .get()
                    .addHeader("key", "efe7926901e330a48efcb6f419b471a5")
                    .build();

            JSONObject json = (JSONObject) new JSONParser().parse(client.newCall(req).execute().body().string());
            return Response.response(json, HttpStatus.OK);
        } catch (Exception e) {
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> cekOngkir(String kota_id, String kurir, HttpServletRequest request) {
//        Connection con = Connect();
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "origin=153&destination="+kota_id+"&weight=1000&courier="+kurir);
            Request req = new Request.Builder()
                    .url("https://api.rajaongkir.com/starter/cost")
                    .post(body)
                    .addHeader("key", "efe7926901e330a48efcb6f419b471a5")
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();
            JSONObject json = (JSONObject) new JSONParser().parse(client.newCall(req).execute().body().string());
            return Response.response(json, HttpStatus.OK);
        } catch (Exception e) {
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
