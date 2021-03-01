package com.example.sicrediapp.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteCountDTO {

    private Long votesYes;
    private Long votesNo;
}
