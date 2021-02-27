package com.example.sicrediapp.model.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    private Long id;
    private Long duration;
    private Boolean isOpen;
}
