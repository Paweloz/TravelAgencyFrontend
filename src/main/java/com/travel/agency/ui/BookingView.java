package com.travel.agency.ui;

import com.travel.agency.domain.BookingDto;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/booking", layout = MainView.class)
public class BookingView extends VerticalLayout {

    private Grid<BookingDto> bookingList = new Grid<>(BookingDto.class);

    public BookingView() {
        add(bookingList);
    }
}
