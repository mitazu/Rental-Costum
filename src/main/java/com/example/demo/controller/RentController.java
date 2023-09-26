package com.example.demo.controller;

import com.example.demo.dao.kostum.KostumDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RentController {
    private final KostumDao kostumDao;

    @GetMapping("/rent")
    public String getKostum(Model model){
        System.out.println("cek");
        List<LinkedHashMap<String,String>> kostum = kostumDao.getDataKostum2("","","","","");
        model.addAttribute("kostum",kostum);
        System.out.println("test masukkkk");
        return "rentView";
    }

    @GetMapping("/getKostum")
    public String cariKostum(@RequestParam(name = "sortby", required = false) String sortby,
                             @RequestParam(name = "nama", required = false) String nama,
                             @RequestParam(name = "minharga", required = false) String minharga,
                             @RequestParam(name = "maxharga", required = false) String maxharga,
                             @RequestParam(name = "kategori", required = false) String kategori,
                             Model model){
        System.out.println("cekkk");
        List<LinkedHashMap<String,String>> kostum = kostumDao.getDataKostum2(sortby,nama,kategori,minharga,maxharga);
        model.addAttribute("kostum",kostum);
        System.out.println("test cariii");
        return "rentView";
    }
    @GetMapping("/getKostumDetail")
    public String getKostumDetail(@RequestParam(name = "id") String id,
                                  Model model){
        System.out.println("cek detail");
        List<LinkedHashMap<String,String>> kostum = kostumDao.getDataKostumDetail2(id);
        System.out.println("kostum"+kostum);
        model.addAttribute("kostum",kostum);
        return "rentDetailView";
    }

    @GetMapping("/tampilkanModul")
    public String tampilkanModul() {
        // Mengembalikan nama modul JSP yang ingin ditampilkan
        return "loginModul";
    }

}
