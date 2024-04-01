package com.example.firebasemessaging.datasource.vo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = "test", name = "push_queue")
public class PushQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_idx")
    private Integer queueIdx;

    @Column(name = "topic")
    private String topic;

    @Column(name = "memb_id")
    private String membId;

    @Column(name = "msg_title")
    private String msgTitle;

    @Column(name = "msg_contents")
    private String msgContents;

    @Column(name = "msg_link")
    private String msgLink;

    @Column(name = "send_stat")
    private String sendStat;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    @Column(name = "result_date")
    private LocalDateTime resultDate;

    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate;

    @Column(name = "cre_date")
    private LocalDateTime creDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;

    @Builder
    public PushQueue(Integer queueIdx, String topic, String membId, String msgTitle, String msgContents, String msgLink, String sendStat, LocalDateTime sendDate, LocalDateTime resultDate, LocalDateTime feedbackDate, LocalDateTime creDate, LocalDateTime updDate) {
        this.queueIdx = queueIdx;
        this.topic = topic;
        this.membId = membId;
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
}
