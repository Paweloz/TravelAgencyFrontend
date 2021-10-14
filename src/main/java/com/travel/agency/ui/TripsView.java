package com.travel.agency.ui;

import com.travel.agency.domain.*;
import com.travel.agency.security.SecurityConfiguration;
import com.travel.agency.service.BookinigService;
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
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;


@Route(value = "/trips", layout = MainView.class)
public class TripsView extends VerticalLayout {

    private final TravelService travelService;
    private final TripService tripService;
    private final HotelService hotelService;
    private final BookinigService bookinigService;
    private final Grid<TripsDto> searchingResults = new Grid<>(TripsDto.class);
    private final ComboBox<TravelDestinations> selectDestination = new ComboBox<>();
    private final ComboBox<TravelOrigins> selectOrigin = new ComboBox<>();
    private final Button submit = new Button("Search");
    private List<TripsDto> tripsToDisplay = new ArrayList<>();
    private Label message = new Label("Please choose your departure date and destination");
    private HorizontalLayout browserSection = new HorizontalLayout();

    public TripsView(TravelService travelService,
                     TripService tripService,
                     HotelService hotelService,
                     BookinigService bookinigService) {
        this.bookinigService = bookinigService;
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
        searchingResults.addItemDoubleClickListener(event -> bookTrip(event.getItem()) );
        browserSection.add(selectOrigin, selectDestination, submit, message);
        browserSection.setDefaultVerticalComponentAlignment(Alignment.END);
    }

    private void bookTrip(TripsDto chosenTrip) {
        UserDto currentUser = UserDtoMap.getInstance().getCustomerMap().get(VaadinSession.getCurrent());
        BookingDto bookingToSave = bookinigService.createBooking(chosenTrip, currentUser);
        bookinigService.bookTrip(bookingToSave);
    }

    private void searchForTrips() {

        if (selectOrigin.getValue() != null && selectDestination.getValue() != null) {
            collectDataAndBuildOffer();
            searchingResults.setItems(tripsToDisplay);
        } else {
            message.setText("Both values are required");
        }
    }

    private void collectDataAndBuildOffer() {
        List<Travel> fetchedTravel = getTravels();
        String hotelId = hotelService.getHotelId(selectDestination.getValue().toString());
        HotelDto hotelDto = getHotelDetails(fetchedTravel, hotelId);
        tripsToDisplay = tripService.buildOffer(fetchedTravel, hotelDto);
        setMessage(fetchedTravel);
    }

    private HotelDto getHotelDetails(List<Travel> fetchedTravel, String hotelId) {
        return hotelService.getRooms(hotelId,
                fetchedTravel.get(0).getStartDate(),
                fetchedTravel.get(0).getFinishDate());
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
