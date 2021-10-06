package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/batch")
public class BatchUploadController {
    @GetMapping("/uploadPage")
    public ModelAndView getBatchUploadPage(){
        ModelAndView uploadPage = new ModelAndView("batchUpload");
        return uploadPage;
    }
}
