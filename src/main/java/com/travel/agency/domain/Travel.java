package com.travel.agency.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Travel {
    private String origin;
    private String destination;
    private String startDate;
    private String finishDate;
    private BigDecimal flightPrice;
    private int daysQuantity;
}
