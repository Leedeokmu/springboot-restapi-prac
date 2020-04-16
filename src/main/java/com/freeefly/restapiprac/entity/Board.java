package com.freeefly.restapiprac.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends CommonDateEntity implements Serializable {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;
}
