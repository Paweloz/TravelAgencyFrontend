package com.travel.agency.service;

import com.travel.agency.domain.Room;
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

    public TripsDto buildTrip(Travel travel, Room room) {
        return new TripsDto(
                travel.getOrigin(),
                travel.getDestination(),
                travel.getStartDate(),
                travel.getFinishDate(),
                travel.getDaysQuantity(),
                room.getName(),
                room.getStarRating(),
                calculatePrice(travel, room)
                );
    }

    public List<TripsDto> buildOffer(List<Travel> travels, Room room) {
        List<TripsDto> trips = new ArrayList<>();
        for (Travel travel : travels) {
            trips.add(buildTrip(travel,room));
        }
        return trips;
    }

    private BigDecimal calculatePrice(Travel travel, Room room) {
        BigDecimal hotelPrice = room.getPricePLN().multiply(new BigDecimal(travel.getDaysQuantity()));
        return hotelPrice.add(travel.getFlightPrice());
    }
}
