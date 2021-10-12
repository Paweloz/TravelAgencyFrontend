package com.travel.agency.ui;

import com.travel.agency.domain.TravelDestinations;
import com.travel.agency.domain.TravelDto;
import com.travel.agency.domain.TravelOrigins;
import com.travel.agency.service.TravelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.List;


@Route(value = "/trips", layout = MainView.class)
public class TripsView extends VerticalLayout {

    private final TravelService travelService;
    private final Grid<TravelDto> searchingResults = new Grid<>(TravelDto.class);
    private final ComboBox<TravelDestinations> selectDestination = new ComboBox<>();
    private final ComboBox<TravelOrigins> selectOrigin = new ComboBox<>();
    private final Button submit = new Button("Search");
    private Label message = new Label("Please choose your departure date and destination");
    public static TravelDto chosenTrip;
    private HorizontalLayout browserSection = new HorizontalLayout();

    public TripsView(TravelService travelService) {
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
            String origin = selectOrigin.getValue().getOriginId();
            String destination = selectDestination.getValue().getDestinationId();
            List<TravelDto> fetchedTravel = travelService.findTrips(origin, destination);
            setMessage(fetchedTravel);
            searchingResults.setItems(fetchedTravel);
            searchingResults.addItemDoubleClickListener(item -> findHotel(item.getItem()));
        } else {
            message.setText("Both values are required");
        }
    }

    private static void findHotel(TravelDto trip) {
        System.out.println(trip.toString());
         chosenTrip = trip;
    }

    private void setMessage(List<TravelDto> fetchedTravel) {
        if (fetchedTravel.size() != 0) {
            message.setText("Click twice to book trip");
        } else {
            message.setText("Couldn't find any trips for given parameters");
        }
    }

}
