package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.UserDto;
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
public class UserClient {
    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private final AppProblemService appProblemService;

    public void removeUser(Long id) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint())
                    .queryParam("userId", id)
                    .build()
                    .encode()
                    .toUri();
            restTemplate.delete(uri);

        } catch (RestClientException e) {
            log.warn("Could remove user with id :" + id + " " + e.getMessage());
            appProblemService.reportProblem("Could remove user with id :" + id + " " + e.getMessage());
        }
    }

    public UserDto getUserByName(String username) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint())
                    .queryParam("name", username)
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.getForObject(uri, UserDto.class);
        } catch (RestClientException e) {
            log.warn("Couldn't retrive user : " + username + " " + e.getMessage());
            appProblemService.reportProblem("Couldn't retrive user : " + username + " " + e.getMessage());
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
            log.warn("Customer with name : " + username +" doesn't exist " + e.getMessage());
            appProblemService.reportProblem("Customer with name : " + username +" doesn't exist " + e.getMessage());
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
            log.warn("Couldn't save user in DB " + e.getMessage());
            appProblemService.reportProblem("Couldn't save user in DB " + e.getMessage());
        }
        return false;
    }

    public UserDto getUserById(Long id) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint() + "/getDetails")
                    .queryParam("userId", id)
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.getForObject(uri, UserDto.class);
        } catch ( RestClientException e) {
            log.warn("Could retrieve user with id : " + id +" " + e.getMessage());
            appProblemService.reportProblem("Could retrieve user with id : " + id +" " + e.getMessage());
        }
        return new UserDto();
    }

    public void editUser(UserDto userDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint() +"/edit")
                    .build()
                    .encode()
                    .toUri();
            restTemplate.put(uri, userDto);
        } catch (RestClientException e) {
            log.warn("Could edit user with id : " + userDto.getId() + " " + e.getMessage());
            appProblemService.reportProblem("Could edit user with id : " + userDto.getId() + " " + e.getMessage());
        }
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> modifiableUserList = new ArrayList<>();
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getUserEndpoint() + "/all")
                    .build()
                    .encode()
                    .toUri();
            UserDto[] users = restTemplate.getForObject(uri, UserDto[].class);
            modifiableUserList.addAll(Arrays.asList(users));
            return modifiableUserList;
        } catch ( RestClientException e ) {
            log.warn("Could retrieve users form DB " + e.getMessage());
            appProblemService.reportProblem("Could retrieve users form DB " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
