package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.UserDto;
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
public class UserClient {
    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;

    public UserDto getUserByName(String username) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint())
                    .queryParam("name", username)
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.getForObject(uri, UserDto.class);
        } catch (RestClientException e) {
            log.warn("Couldn't retrive user :" + username);
        }
        return new UserDto();
    }

    public Boolean checkIfExists(String username) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint() + "/exist")
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

    public Boolean saveUserInDB(UserDto userDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint())
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.postForObject(uri, userDto, Boolean.class);
        }catch (RestClientException e) {
            log.warn("Couldn't save user in DB");
        }
        return false;
    }

    public UserDto getUserById(Long id) {
        try {
            
        } catch ( RestClientException e) {
            log.warn("Could retrieve user with id : " + id);
        }
        return new UserDto();
    }
}
