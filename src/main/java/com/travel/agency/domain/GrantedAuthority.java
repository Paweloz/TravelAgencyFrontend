package com.travel.agency.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

    @JsonProperty("authority")
    String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}