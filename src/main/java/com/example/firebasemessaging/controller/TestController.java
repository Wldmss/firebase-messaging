package com.example.firebasemessaging.controller;

import jakarta.servlet.http.HttpServletRequest;
import com.example.firebasemessaging.datasource.dto.LoginDTO;
import com.example.firebasemessaging.datasource.dto.PushDTO;
import com.example.firebasemessaging.datasource.dto.ResultDTO;
import com.example.firebasemessaging.datasource.vo.Account;
import com.example.firebasemessaging.datasource.vo.PushMember;
import com.example.firebasemessaging.datasource.vo.Test;
import com.example.firebasemessaging.service.TestService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @Autowired
    TestService testService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/")
    public String test(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader;
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkServer(){
        return ResponseEntity.ok(true);
    }

    @GetMapping("/test")
    public List<Test> getTest() {
        return testService.findAllBy();
    }

    @PostMapping(value = "/login", produces = "application/json; charset=UTF8")
    public ResponseEntity<ResultDTO> login(@RequestBody LoginDTO loginDTO) {
        String tokenResult = null;
        if(loginDTO.getUsername() != null && loginDTO.getPassword() != null){
            Account account = testService.getAccountByMembId(loginDTO.getUsername());

            if(account != null){
                String[] testToken = {account.getMembId(), account.getPwEndDt().format(formatter)};
                tokenResult = String.join("&", testToken);
            }

            if (loginDTO.getDeviceToken() != null) {
                testService.savePushMember(PushMember.builder()
                        .deviceToken(loginDTO.getDeviceToken())
                        .membId(loginDTO.getUsername())
                        .activate(1)
                        .creDate(LocalDateTime.now())
                        .build());
            }
        }

        ResultDTO result = ResultDTO.builder()
                .token(tokenResult)
                .build();

        return tokenResult != null ? ResponseEntity.ok(result) : null;
    }

    @PostMapping(value = "/login/check", produces = "application/json; charset=UTF8")
    public ResponseEntity<ResultDTO> checkLogin(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        String authorizationHeader = request.getHeader("Authorization");    // username, pw 만료일
//        LoginDTO login = testService.getLogin(authorizationHeader);
        LoginDTO login = LoginDTO.builder()
                .username("91352089")
                .pwEndDt("2024-01-01").build();

        LocalDateTime date = LocalDateTime.parse(login.getPwEndDt(), formatter);
        boolean isSame = testService.checkAccount(login.getUsername(), date);

        if (isSame) {
            if (loginDTO.getDeviceToken() != null) {
                testService.savePushMember(PushMember.builder()
                        .deviceToken(loginDTO.getDeviceToken())
                        .membId(login.getUsername())
                        .activate(1)
                        .creDate(LocalDateTime.now())
                        .build());
            }

            ResultDTO result = ResultDTO.builder()
                    .token(authorizationHeader)
                    .build();

            return ResponseEntity.ok(result);
        }

        return null;
    }

    @PostMapping(value = "/push", produces = "application/json; charset=UTF8")
    public ResponseEntity<?> push(HttpServletRequest request, @RequestBody PushDTO pushDTO) {
        String authorizationHeader = request.getHeader("Authorization");    // username, pw 만료일
//        LoginDTO login = testService.getLogin(authorizationHeader);

        if (pushDTO.getDeviceToken() != null) {
            testService.savePushMember(PushMember.builder()
                    .deviceToken(pushDTO.getDeviceToken())
                    .membId("91352089")
                    .activate(1)
                    .creDate(LocalDateTime.now())
                    .build());
        }

        return ResponseEntity.ok(true);
    }



    @GetMapping("/file")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            // fileName 파라미터로 파일명을 가져온다.
            String fileName = "지니어스 사용자 매뉴얼.pdf";

            // 파일이 실제 업로드 되어있는(파일이 존재하는) 경로를 지정한다.
            String filePath = "C:\\Users\\SQI\\Downloads\\";

            // 경로와 파일명으로 파일 객체를 생성한다.
            File dFile = new File(filePath, fileName);

            // 파일 길이를 가져온다.
            int fSize = (int) dFile.length();

            // 파일이 존재
            if (fSize > 0) {

                // 파일명을 URLEncoder 하여 attachment, Content-Disposition Header로 설정
                String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileName, "UTF-8");
//                String encodedFilename = "attachment; filename=\"" + new String(fileName.getBytes("EUC-KR"),"ISO-8859-1") + "\";";

                // ContentType 설정
                response.setContentType("application/pdf;");   // charset=utf-8  application/octet-stream;

                // Header 설정
                response.setHeader("Content-Disposition", encodedFilename);

                response.setHeader("Content-Transfer-Encoding", "binary");
                response.setHeader("Content-Length", "" + fSize);
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "-1");


                // ContentLength 설정
                response.setContentLengthLong(fSize);

                try (
                    FileInputStream fis = new FileInputStream(dFile);
                    OutputStream out = response.getOutputStream();
                ) {
                    int readCount = 0;
                    byte[] buffer = new byte[1024];
                    while ((readCount = fis.read(buffer)) != -1) {
                        out.write(buffer, 0, readCount);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
//                    throw new RuntimeException("file Save Error");
                }

                /*BufferedInputStream in = null;
                BufferedOutputStream out = null;

                *//* BufferedInputStream
                 *
                java.io의 가장 기본 파일 입출력 클래스
                입력 스트림(통로)을 생성해줌
                사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
                속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
                *//*
                in = new BufferedInputStream(new FileInputStream(dFile));

                *//* BufferedOutputStream
                 *
                java.io의 가장 기본이 되는 파일 입출력 클래스
                출력 스트림(통로)을 생성해줌
                사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
                속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
                *//*
                out = new BufferedOutputStream(response.getOutputStream());

                try {
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;

                    *//*
                    모두 현재 파일 포인터 위치를 기준으로 함 (파일 포인터 앞의 내용은 없는 것처럼 작동)
                    int read() : 1byte씩 내용을 읽어 정수로 반환
                    int read(byte[] b) : 파일 내용을 한번에 모두 읽어서 배열에 저장
                    int read(byte[] b. int off, int len) : 'len'길이만큼만 읽어서 배열의 'off'번째 위치부터 저장
                    *//*
                    while ((bytesRead = in .read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    // 버퍼에 남은 내용이 있다면, 모두 파일에 출력
                    out.flush();
                } finally {
                    *//*
                    현재 열려 in,out 스트림을 닫음
                    메모리 누수를 방지하고 다른 곳에서 리소스 사용이 가능하게 만듬
                    *//*
                    in.close();
                    out.close();
                }*/
            } else {
                throw new FileNotFoundException("파일이 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("cipher")
    public ResponseEntity<String> decrypt(@RequestBody Map<String, Object> param){
        try {
            // 암호화된 문자열을 정의합니다.
            String encryptedData = param.get("encrypt").toString();

            // 암호화에 사용된 키와 IV를 정의합니다.
            String key = param.get("key").toString(); // 32 바이트 키
            String iv = param.get("iv").toString(); // 16 바이트 IV

            // 키와 IV를 바이트 배열로 변환합니다.
            byte[] keyBytes = key.getBytes("UTF-8");
            byte[] ivBytes = iv.getBytes("UTF-8");

            // 복호화 설정을 구성합니다.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Base64로 인코딩된 암호화 데이터를 디코딩합니다.
//            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] encryptedBytes = Hex.decodeHex(encryptedData.toCharArray());

            // 데이터를 복호화합니다.
            byte[] originalBytes = cipher.doFinal(encryptedBytes);
            String originalData = new String(originalBytes, "UTF-8");

            System.out.println("Decrypted: " + originalData);
            return ResponseEntity.ok(originalData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("");
        }
    }

}
