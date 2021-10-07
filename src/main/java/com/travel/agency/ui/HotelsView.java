package com.travel.agency.ui;

import com.travel.agency.domain.Room;
import com.travel.agency.domain.TravelDestinations;
import com.travel.agency.domain.TravelDto;
import com.travel.agency.domain.TravelOrigins;
import com.travel.agency.service.HotelService;
import com.travel.agency.service.TravelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


import java.util.List;

@Route(value = "/hotels", layout = MainView.class)
public class HotelsView extends VerticalLayout {

    private final HotelService hotelService;
    private final Grid<Room> searchingResults = new Grid<>(Room.class);

    public HotelsView(HotelService hotelService) {
        this.hotelService = hotelService;
    }
}
