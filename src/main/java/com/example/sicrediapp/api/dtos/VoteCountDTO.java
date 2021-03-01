package com.example.sicrediapp.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteCountDTO {

    private Long votesYes;
    private Long votesNo;
}
