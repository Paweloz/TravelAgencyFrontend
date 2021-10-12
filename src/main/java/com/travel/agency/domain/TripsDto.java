package com.travel.agency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TripsDto {
    private String origin;
    private String destination;
    private String startDate;
    private String finishDate;
    private Integer daysQuantity;
    private String hotelName;
    private String starRating;
    private BigDecimal totalPrice;
}
