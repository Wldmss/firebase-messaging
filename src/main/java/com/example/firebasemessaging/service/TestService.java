package com.example.firebasemessaging.service;

import com.example.firebasemessaging.datasource.dto.LoginDTO;
import com.example.firebasemessaging.datasource.repository.AccountRepository;
import com.example.firebasemessaging.datasource.repository.PushMemberRepository;
import com.example.firebasemessaging.datasource.repository.TestRepository;
import com.example.firebasemessaging.datasource.vo.Account;
import com.example.firebasemessaging.datasource.vo.PushMember;
import com.example.firebasemessaging.datasource.vo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PushMemberRepository pushMemberRepository;

    public List<Test> findAllBy(){
        return testRepository.findAllBy();
    }

    public Account getAccountByMembId(String membId){
        return accountRepository.findByMembId(membId);
    }

    public boolean checkAccount(String membId, LocalDateTime date){
        Account account = accountRepository.findByMembIdAndPwEndDt(membId, date);
        return account != null;
    }

    public void savePushMember(PushMember pushMember){
        pushMemberRepository.save(pushMember);
    }

    public LoginDTO getLogin(String autorization){
        String[] splitToken = !autorization.isEmpty() ? autorization.split("&") : new String[]{"91352089", ""};
        return LoginDTO.builder()
                .username(splitToken[0])
                .pwEndDt(splitToken[1])
                .build();
    }
}
