package com.freeefly.restapiprac.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Builder
@Entity
public class User {
    @Id @GeneratedValue
    private Long msrl;

    @Column(nullable = false, unique = true, length = 30)
    private String uid;

    @Column(nullable = false, length = 100)
    private String name;



}
