package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.BookingDto;
import com.travel.agency.domain.TripsDto;
import com.travel.agency.service.AppProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingClient {
    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;
    private final AppProblemService appProblemService;

    public BookingDto createBooking(BookingDto bookingDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBookingEndpoint())
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.postForObject(uri, bookingDto, BookingDto.class);
        } catch (RestClientException e) {
            log.warn("Could save booking for user with ID : " + bookingDto.getUserId() );
            appProblemService.reportProblem("Failed to create booking " + e.getMessage());
        }
        return new BookingDto();
    }

    public List<BookingDto> getBookingsByUserId(Long id) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBookingEndpoint() + "/userId")
                    .queryParam("userId", id)
                    .build()
                    .encode()
                    .toUri();
            BookingDto[] bookingDtos = restTemplate.getForObject(uri, BookingDto[].class);
            return bookingDtos != null ? Arrays.asList(bookingDtos) : new ArrayList<>();
        } catch (RestClientException e) {
            log.warn("Couldn't retrieve bookings for user with id : " + id);
            appProblemService.reportProblem("Failed to get users bookings from DB " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void removeBooking(Long id) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBookingEndpoint())
                    .queryParam("bookingId", id)
                    .build()
                    .encode()
                    .toUri();
            restTemplate.delete(uri);
        } catch (RestClientException e) {
            log.warn("Could remove booking with id : " + id);
            appProblemService.reportProblem("Failed to remove booking " + e.getMessage());
        }
    }
}
