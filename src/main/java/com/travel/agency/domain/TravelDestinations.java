package com.travel.agency.domain;

import lombok.Getter;

@Getter
public enum TravelDestinations {
    CORFU("CFU"),
    MALTA("MLA"),
    CRETE("CHQ"),
    RHODOS("RHO"),
    IBIZA("IBZ"),
    CANCUN("CUN"),
    PUNTA_CANA("PUJ"),
    SARDINIA("AHO"),
    MAJORCA("PMI"),
    TENERIFE("TFS");

    private String destinationId;

    TravelDestinations(String destinationId){
        this.destinationId = destinationId;
    }
}
