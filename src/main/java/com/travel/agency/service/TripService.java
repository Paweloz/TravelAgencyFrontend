package com.travel.agency.service;

import com.travel.agency.domain.HotelDto;
import com.travel.agency.domain.Travel;
import com.travel.agency.domain.TripsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    public TripsDto buildTrip(Travel travel, HotelDto hotelDto) {
        return new TripsDto(
                travel.getOrigin(),
                travel.getDestination(),
                travel.getStartDate(),
                travel.getFinishDate(),
                travel.getDaysQuantity(),
                hotelDto.getName(),
                hotelDto.getStarRating(),
                calculatePrice(travel, hotelDto)
                );
    }

    public List<TripsDto> buildOffer(List<Travel> travels, HotelDto hotelDto) {
        List<TripsDto> trips = new ArrayList<>();
        for (Travel travel : travels) {
            trips.add(buildTrip(travel, hotelDto));
        }
        return trips;
    }

    private BigDecimal calculatePrice(Travel travel, HotelDto hotelDto) {
        BigDecimal hotelPrice = hotelDto.getPricePerNight().multiply(new BigDecimal(travel.getDaysQuantity()));
        return hotelPrice.add(travel.getFlightPrice());
    }
}
