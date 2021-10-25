package com.travel.agency.service;

import com.travel.agency.client.LoginClient;
import com.travel.agency.domain.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginClient loginClient;


    public void createLoginEvent(LoginDto loginDto) {
         loginClient.saveLoginEvent(loginDto);
    }
}
