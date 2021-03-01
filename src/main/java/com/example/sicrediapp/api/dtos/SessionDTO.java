package com.example.sicrediapp.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO {

    @Schema(description = "session's duration. In minutes", example = "3", defaultValue = "1")
    private Long duration;
    @Schema(description = "sessions status. Defines if the session is open or not to vote", example = "false", type = "boolean")
    private boolean isOpen;
    @Schema(description = "schedule's id", example = "1")
    private Long scheduleId;
}
