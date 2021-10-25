package com.travel.agency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppProblemDto {
    private Long id;
    private String problemType;
    private LocalDateTime localDateTime;

    public AppProblemDto(String problemType, LocalDateTime localDateTime) {
        this.problemType = problemType;
        this.localDateTime = localDateTime;
    }
}