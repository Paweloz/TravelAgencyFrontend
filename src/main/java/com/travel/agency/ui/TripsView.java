package com.travel.agency.ui;

import com.travel.agency.domain.*;
import com.travel.agency.service.HotelService;
import com.travel.agency.service.TravelService;
import com.travel.agency.service.TripService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;


@Route(value = "/trips", layout = MainView.class)

public class TripsView extends VerticalLayout {

    private final TravelService travelService;
    private final TripService tripService;
    private final HotelService hotelService;
    private final Grid<TripsDto> searchingResults = new Grid<>(TripsDto.class);
    private final ComboBox<TravelDestinations> selectDestination = new ComboBox<>();
    private final ComboBox<TravelOrigins> selectOrigin = new ComboBox<>();
    private final Button submit = new Button("Search");
    private Label message = new Label("Please choose your departure date and destination");
    public static Travel chosenTrip;
    private HorizontalLayout browserSection = new HorizontalLayout();

    public TripsView(TravelService travelService, TripService tripService, HotelService hotelService ) {
        this.tripService = tripService;
        this.hotelService = hotelService;
        this.travelService = travelService;
        setupView();
        add(browserSection, searchingResults);
    }

    private void setupView() {
        selectOrigin.setItems(TravelOrigins.values());
        selectOrigin.isRequired();
        selectOrigin.setPlaceholder("Select Origin");

        selectDestination.setItems(TravelDestinations.values());
        selectDestination.isRequired();
        selectDestination.setPlaceholder("Select Destination");

        submit.addClickListener(event -> searchForTrips());
        browserSection.add(selectOrigin, selectDestination, submit, message);
        browserSection.setDefaultVerticalComponentAlignment(Alignment.END);
    }

    private void searchForTrips() {

        if (selectOrigin.getValue() != null && selectDestination.getValue() != null) {
            List<Travel> fetchedTravel = getTravels();
            String hotelId = hotelService.getHotelId(selectDestination.getValue().toString());
            Room room = hotelService.getRooms(hotelId,
                    fetchedTravel.get(0).getStartDate(),
                    fetchedTravel.get(0).getFinishDate());
            List<TripsDto> tripsToDisplay = tripService.buildOffer(fetchedTravel, room);
            setMessage(fetchedTravel);
            searchingResults.setItems(tripsToDisplay);
        } else {
            message.setText("Both values are required");
        }
    }

    private List<Travel> getTravels() {
        String origin = selectOrigin.getValue().getOriginId();
        String destination = selectDestination.getValue().getDestinationId();
        return travelService.findTravels(origin, destination);
    }

    private void setMessage(List<Travel> fetchedTravel) {
        if (fetchedTravel.size() != 0) {
            message.setText("Click twice to book trip");
        } else {
            message.setText("Couldn't find any trips for given parameters");
        }
    }

}
