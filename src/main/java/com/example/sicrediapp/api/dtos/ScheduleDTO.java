package com.example.sicrediapp.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
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
