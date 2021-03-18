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
public class AssociateResponseDTO {

    @Schema(description = "Associate's id", example = "1")
    private Long id;
    @Schema(description = "Associate's Name", example = "Fernando")
    private String name;
    @Schema(description = "Associate's CPF", example = "01373891000", minimum = "11", maximum = "11")
    private String cpf;
}
