package com.example.sicrediapp.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {

    private Long id;
    private Long duration;
    private boolean isOpen;
}
