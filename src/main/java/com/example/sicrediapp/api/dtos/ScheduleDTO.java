package com.example.sicrediapp.api.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {

    private Long id;
    @NotNull
    private Long duration;
    @NotNull
    private Boolean isOpen;
}
