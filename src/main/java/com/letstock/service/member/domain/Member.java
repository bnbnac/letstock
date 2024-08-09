package com.letstock.service.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long hello;

    public void hello() {
        this.hello = hello + 1;
    }
}
