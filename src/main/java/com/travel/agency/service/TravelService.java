package com.travel.agency.service;

import com.travel.agency.client.TravelClient;
import com.travel.agency.domain.TravelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelClient travelClient;

    public List<TravelDto> getLastMinuteTrips() {
        return new ArrayList<>();
    }
    public List<TravelDto> findTrips(String origin, String destination) {
        List<TravelDto> fetchedTrips = travelClient.getAvaliableTrips(origin, destination);
        return filterTrips(fetchedTrips);
    }

    private List<TravelDto> filterTrips(List<TravelDto> fetchedTrips) {
        List<TravelDto> filteredTrips = new ArrayList<>();
        for ( TravelDto travelDto : fetchedTrips ){
            int daysQuantity = LocalDate.parse(travelDto.getFinishDate()).compareTo(LocalDate.parse(travelDto.getStartDate()));
            travelDto.setDaysQuantity(daysQuantity);
            if (daysQuantity >= 5 && daysQuantity <= 14) {
                filteredTrips.add(travelDto);
            }
        }
        return filteredTrips;
    }
}
