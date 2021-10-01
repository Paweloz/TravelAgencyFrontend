package com.travel.agency.ui;

import com.travel.agency.domain.TravelDestinations;
import com.travel.agency.domain.TravelDto;
import com.travel.agency.service.TravelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;


@Route(value = "/trips", layout = MainView.class)
public class TripsView extends VerticalLayout {

    private final TravelService travelService;
    private final Grid<TravelDto> searchingResults = new Grid<>(TravelDto.class);
    private final ComboBox<TravelDestinations> selectDestination = new ComboBox<>();
    private final DatePicker datePicker = new DatePicker();
    private final Button submit = new Button("Search");
    private Label message = new Label("Please choose your departure date and destination");
    private HorizontalLayout browserSection = new HorizontalLayout();

    public TripsView(TravelService travelService) {
        this.travelService = travelService;
        setupView();
        add(browserSection, searchingResults);
    }

    private void setupView() {
        datePicker.setPlaceholder("Pick starting date");
        datePicker.isRequired();
        datePicker.setClearButtonVisible(true);

        selectDestination.setItems(TravelDestinations.values());
        selectDestination.isRequired();
        selectDestination.setPlaceholder("Select Destination");

        submit.addClickListener(event -> searchForTrips());
        browserSection.add(datePicker, selectDestination, submit, message);
        browserSection.setDefaultVerticalComponentAlignment(Alignment.END);
    }

    private void searchForTrips() {

        if (!datePicker.isEmpty() && selectDestination.getValue() != null) {
            LocalDate departureDate = datePicker.getValue();
            String destination = selectDestination.getValue().toString();
            travelService.findTrips(departureDate, destination);
        } else {
            message.setText("Both values are required");
        }
    }
}
