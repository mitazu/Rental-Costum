package com.example.demo.controller;

import com.example.demo.dao.login.LoginDao;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginDao loginDao;

    private final AuthenticationManager authenticationManager;
    public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/webapp/images";

    @RequestMapping("/masuk")
    public ModelAndView masuk(){
        ModelAndView mav = new ModelAndView("loginView");
        mav.addObject("signin", new LoginRequest());
        mav.addObject("signup", new RegisterRequest());
        return mav;
    }

    @PostMapping("/login2")
    public String login2(@ModelAttribute("signin")LoginRequest loginRequest, HttpSession session){
        LinkedHashMap<String, String> linkedHashMap = loginDao.doLogin2(loginRequest);
        if(linkedHashMap.get("message").equals("Success")){
            System.out.println(linkedHashMap);
            session.setAttribute("username",linkedHashMap.get("user_name"));
            System.out.println("Succeeesssss"+linkedHashMap.get("user_name")+linkedHashMap.get("message"));
            return "redirect:/dashboard";
        }else{
            System.out.println("gagaglllll"+linkedHashMap.get("user_name")+linkedHashMap.get("message"));
            return "redirect:/masuk";
        }
    }
    @PostMapping("/register2")
    public String doRegister2(
            @RequestParam("fileimage")MultipartFile multipartFile,
            RegisterRequest registerRequest) throws Exception
    {
        System.out.println("ayo register");
        String filename = registerRequest.getUsername()+ multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length()-4);
        Path filenameAndPath = Paths.get(uploadDirectory,filename);
        try {
            Files.write(filenameAndPath, multipartFile.getBytes());
            System.out.println("berhasil save foto");
        }catch (IOException e){
            System.out.println("gagal save foto??");
            e.printStackTrace();
        }
        registerRequest.setFoto(filename);
        String result = loginDao.doRegister2(registerRequest);
        System.out.println("Result register: "+result);

        return "redirect:/masuk";

    }

}
