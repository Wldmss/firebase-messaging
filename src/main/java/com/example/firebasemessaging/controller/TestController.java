package com.example.firebasemessaging.controller;

import jakarta.servlet.http.HttpServletRequest;
import com.example.firebasemessaging.datasource.dto.LoginDTO;
import com.example.firebasemessaging.datasource.dto.PushDTO;
import com.example.firebasemessaging.datasource.dto.ResultDTO;
import com.example.firebasemessaging.datasource.vo.Account;
import com.example.firebasemessaging.datasource.vo.PushMember;
import com.example.firebasemessaging.datasource.vo.Test;
import com.example.firebasemessaging.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @GetMapping("/test")
    public List<Test> getTest() {
        return testService.findAllBy();
    }

    @PostMapping(value = "/login", produces = "application/json; charset=UTF8")
    public ResponseEntity<ResultDTO> login(@RequestBody LoginDTO loginDTO) {
        Account account = testService.getAccountByMembId(loginDTO.getUsername());

        String[] testToken = {account.getMembId(), account.getPwEndDt().format(formatter)};
        String tokenResult = String.join("&",testToken);

        ResultDTO result = ResultDTO.builder()
                .token(tokenResult)
                .build();

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/login/check")
    public ResponseEntity<ResultDTO> checkLogin(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");    // username, pw 만료일
        LoginDTO login = testService.getLogin(authorizationHeader);

        LocalDateTime date = LocalDateTime.parse(login.getPwEndDt(), formatter);
        boolean isSame = testService.checkAccount(login.getUsername(), date);

        if(isSame){
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
        LoginDTO login = testService.getLogin(authorizationHeader);

        if(pushDTO.getDeviceToken() != null){
            testService.savePushMember(PushMember.builder()
                    .deviceToken(pushDTO.getDeviceToken())
                    .membId(login.getUsername())
                    .activate(1)
                    .creDate(LocalDateTime.now())
                    .build());
        }

        return ResponseEntity.ok(true);
    }

}
