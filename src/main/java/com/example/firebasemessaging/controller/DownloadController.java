package com.example.firebasemessaging.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/download")
public class DownloadController {

    @GetMapping("")
    public String appDownload(Model model){
        return "staging";
    }


}
