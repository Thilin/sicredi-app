package com.example.sicrediapp.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_SESSION")
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SES_ID", nullable = false)
    private Long id;

    @Column(name = "SES_DURATION", columnDefinition = "bigint unsigned default 1", nullable = false)
    private Long duration;

    @Column(name = "SES_ISOPEN", columnDefinition = "tinyint default 0", nullable = false)
    private boolean isOpen;

    @OneToOne
    @JoinColumn(name = "SES_SCH_ID", referencedColumnName = "SCH_ID", nullable = false)
    private Schedule schedule;
}
