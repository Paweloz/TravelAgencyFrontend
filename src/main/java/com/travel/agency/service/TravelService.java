package com.travel.agency.service;

import com.travel.agency.client.TravelClient;
import com.travel.agency.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelClient travelClient;

    public List<Travel> getLastMinuteTrips() {
        return new ArrayList<>();
    }
    public List<Travel> findTravels(String origin, String destination) {
        List<Travel> fetchedTrips = travelClient.getAvaliableTrips(origin, destination);
        return filterTravels(fetchedTrips);
    }

    private List<Travel> filterTravels(List<Travel> fetchedTravels) {
        List<Travel> filteredTrips = new ArrayList<>();
        for ( Travel travel : fetchedTravels ){
            int daysQuantity = LocalDate.parse(travel.getFinishDate()).compareTo(LocalDate.parse(travel.getStartDate()));
            travel.setDaysQuantity(daysQuantity);
            if (daysQuantity >= 5 && daysQuantity <= 14) {
                filteredTrips.add(travel);
            }
        }
        return filteredTrips;
    }
}
