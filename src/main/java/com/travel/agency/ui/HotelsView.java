package com.travel.agency.ui;

import com.travel.agency.domain.Room;
import com.travel.agency.domain.TravelDto;
import com.travel.agency.service.HotelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "/hotels", layout = MainView.class)
public class HotelsView extends VerticalLayout {

    private final HotelService hotelService;
    private TravelDto chosenTrip;
    private final Grid<Room> searchingResults = new Grid<>(Room.class);
//    private Button getRoomsButton = new Button("Get Rooms");
//    private HorizontalLayout banner = new HorizontalLayout(getRoomsButton);

    public HotelsView(HotelService hotelService) {
        this.hotelService = hotelService;
//        getRoomsButton.addClickListener(event -> searchingResults.setItems(getRoomsList()));
        searchingResults.setItems(getRoomsList());
        add(searchingResults);
    }

    private Room getRoomsList() {
        Room avaliableRoom = new Room();
        if(TripsView.chosenTrip != null) {
            chosenTrip = TripsView.chosenTrip;
            String hotelId = hotelService.getHotelId(chosenTrip.getDestination());
            avaliableRoom = hotelService.getRooms(
                    hotelId,
                    chosenTrip.getStartDate(),
                    chosenTrip.getFinishDate());
        }
        return avaliableRoom;
    }
}
