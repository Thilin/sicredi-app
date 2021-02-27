package com.example.sicrediapp.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_SCHEDULE")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCH_ID")
    private Long id;

    @Column(name = "SCH_DURATION")
    private Long duration;
    @Column(name = "SCH_ISOPEN")
    private boolean isOpen;
}
