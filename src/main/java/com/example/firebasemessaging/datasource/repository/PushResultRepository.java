package com.example.firebasemessaging.datasource.repository;

import com.example.firebasemessaging.datasource.vo.PushResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushResultRepository extends JpaRepository<PushResult, Integer> {

}
