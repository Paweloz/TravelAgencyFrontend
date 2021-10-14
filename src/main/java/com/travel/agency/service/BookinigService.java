package com.travel.agency.service;

import com.travel.agency.client.BookingClient;
import com.travel.agency.domain.BookingDto;
import com.travel.agency.domain.TripsDto;
import com.travel.agency.domain.UserDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Service
public class BookinigService {
    private final BookingClient bookingClient;

    public void bookTrip(BookingDto bookingDto) {
        bookingClient.createBooking(bookingDto);
    }

    public BookingDto createBooking(TripsDto tripsDto, UserDto userDto) {
        return new BookingDto(
                tripsDto.getOrigin(),
                tripsDto.getDestination(),
                LocalDate.parse(tripsDto.getStartDate()),
                LocalDate.parse(tripsDto.getFinishDate()),
                tripsDto.getTotalPrice(),
                userDto.getId()
        );
    }
}
