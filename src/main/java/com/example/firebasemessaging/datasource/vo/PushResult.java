package com.example.firebasemessaging.datasource.vo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = "test", name = "push_result")
public class PushResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer Idx;

    @Column(name = "queue_idx")
    private Integer queueIdx;

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "topic")
    private String topic;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "memb_id")
    private String membId;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    @Column(name = "step")
    private String step;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_dtl")
    private String errorDtl;

    @Builder
    public PushResult(Integer idx, Integer queueIdx, String pushToken, String topic, String deviceToken, String membId, LocalDateTime sendDate, String step, String errorCode, String errorDtl) {
        Idx = idx;
        this.queueIdx = queueIdx;
        this.pushToken = pushToken;
        this.topic = topic;
        this.deviceToken = deviceToken;
        this.membId = membId;
        this.sendDate = sendDate;
        this.step = step;
        this.errorCode = errorCode;
        this.errorDtl = errorDtl;
    }
}
