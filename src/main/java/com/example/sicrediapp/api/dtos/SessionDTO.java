package com.example.sicrediapp.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

    private Long duration;
    private boolean isOpen;
    private Long scheduleId;
}
