package com.weg.infoweg.modules.classes.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;


}
