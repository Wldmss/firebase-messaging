package com.example.firebasemessaging.datasource.repository;

import com.example.firebasemessaging.datasource.vo.PushMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PushMemberRepository extends JpaRepository<PushMember, String> {

    @Transactional
    @Modifying
    @Query("update PushMember m set m.activate = 0, m.updDate = :now where m.deviceToken in :deviceTokenList")
    void disableDeviceToken(LocalDateTime now, List<String> deviceTokenList);

}
