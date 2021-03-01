package com.example.sicrediapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_VOTATION")
public class Votation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOT_ID")
    private Long id;

    @Column(name = "VOT_VOTE")
    private Boolean vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOT_ACD_ID", referencedColumnName = "ACD_ID", nullable = false)
    private Associate associate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOT_SES_ID", referencedColumnName = "SES_ID", nullable = false)
    private Session session;
}
