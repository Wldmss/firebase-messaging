package com.example.firebasemessaging.datasource.repository;

import com.example.firebasemessaging.datasource.vo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByMembId(String membId);
    Account findByMembIdAndPwEndDt(String membId, LocalDateTime date);
}
