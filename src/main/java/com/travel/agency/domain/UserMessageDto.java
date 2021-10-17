package com.travel.agency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageDto {
    private Long id;
    private LocalDate date;
    private String message;
    private Long userId;

    public UserMessageDto(LocalDate date, String message, Long userId) {
        this.date = date;
        this.message = message;
        this.userId = userId;
    }
}
