package com.travel.agency.service;

import com.travel.agency.client.HotelClient;
import com.travel.agency.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelClient hotelClient;

    public String getHotelId(String location) {
       return hotelClient.getHotelId(location);
    }

    public Room getRooms(String hotelId, String checkIn, String checkOut) {
        return hotelClient.getRooms(hotelId, checkIn, checkOut);
    }
}
