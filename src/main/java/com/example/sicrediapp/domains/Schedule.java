package com.example.sicrediapp.domains;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    private Long id;
    private Long duration;
    private boolean isOpen;
}
