package com.travel.agency.domain;

import lombok.Getter;

@Getter
public enum TravelOrigins {
    KATOWICE("KTW"),
    KRAKÓW("KRK"),
    WARSZAWA_CHOPIN("WAW"),
    WARSZAWA_MODLIN("WMI"),
    WROCŁAW("WRO"),
    POZNAŃ("POZ"),
    GDAŃSK("GDN");

    private String originId;

    TravelOrigins(String originId) {
        this.originId = originId;
    }
}
