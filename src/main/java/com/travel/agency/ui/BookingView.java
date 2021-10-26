package com.travel.agency.ui;

import com.travel.agency.domain.BookingDto;
import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.service.BookinigService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.awt.*;
import java.util.List;

@Route(value = "/booking", layout = MainView.class)
public class BookingView extends VerticalLayout {

    private final BookinigService bookinigService;
    private Grid<BookingDto> bookingList = new Grid<>(BookingDto.class);
    private H2 header = new H2("Manage your bookings");

    public BookingView(BookinigService bookinigService) {
        this.bookinigService = bookinigService;
        add(header, bookingList);
        init();
    }

    public void init() {
        bookingList.setItems(retrieveBookings());
        bookingList.addComponentColumn(item -> createButton(bookingList, item)).setHeader("Action");
        bookingList.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private List<BookingDto> retrieveBookings() {
        UserDto user = UserDtoMap.getInstance().getCustomerMap().get(VaadinSession.getCurrent());
        return bookinigService.getBookingsForUser(user.getId());
    }

    private Button createButton(Grid<BookingDto> bookingList, BookingDto item) {
        Button removeButton = new Button("Remove");
        removeButton.addClickListener(event -> {
            bookinigService.removeBooking(item);
            bookingList.setItems(retrieveBookings());
        });
        return removeButton;
    }
}
