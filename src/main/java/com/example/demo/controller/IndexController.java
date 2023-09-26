package com.example.demo.controller;

import com.example.demo.dao.kostum.KostumDao;
import com.example.demo.dao.login.LoginDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final KostumDao kostumDao;
    @GetMapping("/")
    public RedirectView index(){
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String home(Model model){
        List<LinkedHashMap<String, String>> kostumHome = kostumDao.getDataKostum2("","","","","");
        System.out.println("DATA :"+kostumHome);
        model.addAttribute("kostumHome", kostumHome);
        return "homeView";
    }


//
//    @GetMapping("/dashboard")
//    public String dashboard(){
//        return "dashboard";
//    }
}
