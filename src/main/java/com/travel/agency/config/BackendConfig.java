package com.travel.agency.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BackendConfig {
    @Value("${customer.endpoint}")
    private String customerEndpoint;
    @Value("${travel.endpoint}")
    private String travelEndpoint;
    @Value("${login.endpoint}")
    private String loginEndpoint;
}
