package com.example.firebasemessaging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

    @GetMapping("/file")
    public String file(Model model){
        return "test";
    }
}
