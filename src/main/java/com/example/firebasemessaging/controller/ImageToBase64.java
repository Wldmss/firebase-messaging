package com.example.firebasemessaging.controller;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageToBase64 extends ImageConverter<File,String>{

    @Override
    public String convert(File file) throws IOException {
        byte [] fileContent = FileUtils.readFileToByteArray(file);
        return  Base64.getEncoder().encodeToString(fileContent);
    }
}