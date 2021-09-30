package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerClient {
    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;

    public CustomerDto getCustomerByName(String username) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getCustomerEndpoint())
                    .queryParam("name", username)
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.getForObject(uri, CustomerDto.class);
        } catch (RestClientException e) {
            log.warn("Couldn't retrive user :" + username);
        }
        return new CustomerDto();
    }

    public Boolean checkIfExists(String username) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getCustomerEndpoint() + "/exist")
                    .queryParam("name", username)
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.getForObject(uri, Boolean.class);
        } catch (RestClientException e) {
            log.warn("Customer with name : " + username +" doesn't exist");
        }
        return false;
    }

    public Boolean saveCustomerInDB(CustomerDto customerDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getCustomerEndpoint())
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.postForObject(uri, customerDto, Boolean.class);
        }catch (RestClientException e) {
            log.warn("Couldn't save user in DB");
        }
        return false;
    }
}
