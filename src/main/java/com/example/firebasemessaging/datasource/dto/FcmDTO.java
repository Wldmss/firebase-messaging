package com.example.firebasemessaging.datasource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.firebasemessaging.datasource.vo.PushQueue;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FcmDTO {
    private Integer queueIdx;
    private String topic;
    private String membId;
    private String deviceToken;
    private String msgTitle;
    private String msgContents;
    private String msgLink;
    private String sendStat;
    private LocalDateTime sendDate;
    private LocalDateTime resultDate;
    private LocalDateTime feedbackDate;
    private LocalDateTime creDate;
    private LocalDateTime updDate;

    @Builder
    public FcmDTO(Integer queueIdx, String topic, String membId, String deviceToken, String msgTitle, String msgContents, String msgLink, String sendStat, LocalDateTime sendDate, LocalDateTime resultDate, LocalDateTime feedbackDate, LocalDateTime creDate, LocalDateTime updDate) {
        this.queueIdx = queueIdx;
        this.topic = topic;
        this.membId = membId;
        this.deviceToken = deviceToken;
        this.msgTitle = msgTitle;
        this.msgContents = msgContents;
        this.msgLink = msgLink;
        this.sendStat = sendStat;
        this.sendDate = sendDate;
        this.resultDate = resultDate;
        this.feedbackDate = feedbackDate;
        this.creDate = creDate;
        this.updDate = updDate;
    }

    public FcmDTO(PushQueue pushQueue, String deviceToken) {
        this.queueIdx = pushQueue.getQueueIdx();
        this.topic = pushQueue.getTopic();
        this.membId = pushQueue.getMembId();
        this.deviceToken = deviceToken;
        this.msgTitle = pushQueue.getMsgTitle();
        this.msgContents = pushQueue.getMsgContents();
        this.msgLink = pushQueue.getMsgLink();
        this.sendStat = pushQueue.getSendStat();
        this.sendDate = pushQueue.getSendDate();
        this.resultDate = pushQueue.getResultDate();
        this.feedbackDate = pushQueue.getFeedbackDate();
        this.creDate = pushQueue.getCreDate();
        this.updDate = pushQueue.getUpdDate();
    }
}
