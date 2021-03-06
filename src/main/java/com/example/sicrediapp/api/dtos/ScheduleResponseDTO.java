package com.example.sicrediapp.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {

    @Schema(description = "Schedule's id", example = "1")
    private Long id;
    @NotEmpty
    @Schema(description = "Schedule description", example = "Dividendos")
    private String description;
}
