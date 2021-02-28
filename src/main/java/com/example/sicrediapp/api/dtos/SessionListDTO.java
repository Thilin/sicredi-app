package com.example.sicrediapp.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionListDTO {

    @Schema(description = "session's id", example = "1")
    private Long id;
    @Schema(description = "session's duration. In minutes", example = "3", defaultValue = "1")
    private Long duration;
    @Schema(description = "sessions status. Defines if the session is open or not to vote", example = "false", defaultValue = "false")
    private boolean isOpen;
    @Schema(description = "schedule's id", example = "1")
    private Long scheduleId;
}
