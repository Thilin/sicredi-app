package com.example.sicrediapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_SESSION")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SES_ID")
    private Long id;

    @Column(name = "SES_DURATION", columnDefinition = "bigint unsigned default 1")
    private Long duration;

    @Column(name = "SES_ISOPEN")
    private boolean isOpen;

    @OneToOne
    @JoinColumn(name = "SES_SCH_ID", referencedColumnName = "SCH_ID")
    private Schedule schedule;
}
