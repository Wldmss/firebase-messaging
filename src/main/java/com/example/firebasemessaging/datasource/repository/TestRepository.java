package com.example.firebasemessaging.datasource.repository;

import com.example.firebasemessaging.datasource.vo.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    List<Test> findAllBy();
}
