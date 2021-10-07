package com.travel.agency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Room {
    private String hotelName;
    private String roomName;
    private BigDecimal price;
}
