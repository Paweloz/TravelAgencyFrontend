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
    public List<TravelDto> findTrips(LocalDate departureDate, String destination) {
        return travelClient.getAvaliableTrips(departureDate.toString(), destination);
    }
}
