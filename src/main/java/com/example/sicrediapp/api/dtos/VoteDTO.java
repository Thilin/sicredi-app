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
public class VoteDTO {

    @Schema(description = "Schedule's id", example = "1")
    private Long scheduleId;
    @Schema(description = "Associate's id", example = "1")
    private Long associateId;
    @Schema(description = "session's id", example = "1")
    private Long sessionId;

    String vote;
}
