package com.example.demo.controllerSwag;

import com.example.demo.dao.kostum.KostumDao;
import com.example.demo.model.KostumRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "KOSTUM")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RequestMapping("/kostum")
@RestController
@RequiredArgsConstructor
public class KostumCont {
    private final KostumDao kostumDao;

    @Operation(summary = "GET KATEGORI")
    @GetMapping("/getKategori")
    public ResponseEntity<?> getKategori(HttpServletRequest request) {

        return kostumDao.getKategori(request);
    }

    @Operation(summary = "GET DATA KOSTUM")
    @GetMapping("/getDataKostum")
    public ResponseEntity<?> getDataKostum(
            @RequestParam (required = false,defaultValue = "") String sortby,
            @RequestParam (required = false,defaultValue = "") String custom_name,
            @RequestParam (required = false,defaultValue = "") String kategori_id,
            @RequestParam (required = false, defaultValue = "") String min_price,
            @RequestParam (required = false, defaultValue = "") String max_price,
            HttpServletRequest request
    ){
        return kostumDao.getDataKostum(sortby,custom_name,kategori_id,min_price,max_price,request);
    }

    @Operation(summary = "SET DATA KOSTUM")
    @PostMapping("/setDataKostum")
    public ResponseEntity<?> setDataKostum(
            @RequestBody KostumRequest kostumRequest,
            HttpServletRequest request
    ){
        return kostumDao.setDataKostum(kostumRequest,request);
    }
    @Operation(summary = "DELETE DATA KOSTUM")
    @PostMapping("/deleteDataKostum")
    public ResponseEntity<?> deleteDataKostum(
            @RequestParam (required = false,defaultValue = "") String costum_id,
            HttpServletRequest request
    ){
        return kostumDao.deleteDataKostum(costum_id,request);
    }

//    @Operation(summary = "GET DATA KOSTUM DETAIL")
//    @PostMapping("/getDataKostumDetail")
//    public ResponseEntity<?> getDataKostumDetail(
//            @RequestParam (required = false,defaultValue = "") String costum_id,
//            HttpServletRequest request
//    ){
//        return kostumDao.getDataKostumDetail(costum_id,request);
//    }
}
