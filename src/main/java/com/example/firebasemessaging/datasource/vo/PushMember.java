package com.example.firebasemessaging.datasource.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = "test", name = "push_member")
public class PushMember {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "idx")
//    private Integer idx;

    @Id
    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "memb_id")
    private String membId;

    @Column(name = "activate")
    private Integer activate;

    @Column(name = "cre_date")
    private LocalDateTime creDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;

    @Builder
    public PushMember(String deviceToken, String membId, Integer activate, LocalDateTime creDate, LocalDateTime updDate) {
        this.deviceToken = deviceToken;
        this.membId = membId;
        this.activate = activate;
        this.creDate = creDate;
        this.updDate = updDate;
    }
}
