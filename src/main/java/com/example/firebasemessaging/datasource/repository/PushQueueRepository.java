package com.example.firebasemessaging.datasource.repository;

import com.example.firebasemessaging.datasource.dto.FcmDTO;
import com.example.firebasemessaging.datasource.vo.PushQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushQueueRepository extends JpaRepository<PushQueue, Integer> {

    @Query("select new com.example.firebasemessaging.datasource.dto.FcmDTO(q, m.deviceToken) " +
            "from PushQueue q " +
            "left join PushMember m on q.membId = m.membId and m.activate = 1 " +
            "where q.sendStat = '0002' and q.topic is null and q.membId is not null and m.deviceToken is not null")
    List<FcmDTO> getPushQueue();

    @Query("select new com.example.firebasemessaging.datasource.dto.FcmDTO(q, m.deviceToken) " +
            "from PushQueue q " +
            "left join PushMember m on q.membId = m.membId and m.activate = 1 " +
            "where q.sendStat = '0002' and q.topic is not null and q.membId is null")
    List<FcmDTO> getTopicPushQueue();
}