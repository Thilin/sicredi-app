package com.example.sicrediapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_ASSOCIATE")
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACD_ID")
    private Long id;

    @Column(name = "ACD_CPF")
    private String cpf;

    @Column(name = "ACD_NAME")
    private String name;
}
