package com.example.firebasemessaging.datasource.vo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = "test", name = "push_master")
public class PushMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;

    @Column(name = "type")
    private String type;

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Builder
    public PushMaster(Integer idx, String type, String pushToken, LocalDateTime regDate, LocalDateTime endDate) {
        this.idx = idx;
        this.type = type;
        this.pushToken = pushToken;
        this.regDate = regDate;
        this.endDate = endDate;
    }
}
