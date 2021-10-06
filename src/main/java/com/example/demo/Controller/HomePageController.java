package com.example.demo.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping("/homepage")
    public String getHomePage(){
        return "homepage";
    }

    @GetMapping("/contact")
    public String getContactPage(){return "contact";}
}
