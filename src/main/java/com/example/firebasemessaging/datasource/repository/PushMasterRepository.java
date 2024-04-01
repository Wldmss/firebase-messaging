package com.example.firebasemessaging.datasource.repository;

import com.example.firebasemessaging.datasource.vo.PushMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PushMasterRepository extends JpaRepository<PushMaster, Integer> {
    PushMaster findByTypeAndEndDateAfter(String type, LocalDateTime now);
}
