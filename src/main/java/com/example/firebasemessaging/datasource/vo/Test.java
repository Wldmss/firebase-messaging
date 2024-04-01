package com.example.firebasemessaging.datasource.vo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = "test", name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @Builder
    public Test(Integer id, String value) {
        this.id = id;
        this.value = value;
    }
}
