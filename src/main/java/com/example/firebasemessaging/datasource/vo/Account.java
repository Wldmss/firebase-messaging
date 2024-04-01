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
@Table(schema = "test", name = "account")
public class Account {
    @Id
    @Column(name = "memb_id")
    private String membId;

    @Column(name = "pw_end_dt")
    private LocalDateTime pwEndDt;

    @Builder
    public Account(String membId, LocalDateTime pwEndDt) {
        this.membId = membId;
        this.pwEndDt = pwEndDt;
    }
}
