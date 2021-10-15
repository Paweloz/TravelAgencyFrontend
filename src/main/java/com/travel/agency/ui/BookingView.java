package com.travel.agency.ui;

import com.travel.agency.domain.BookingDto;
import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.service.BookinigService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;

@Route(value = "/booking", layout = MainView.class)
public class BookingView extends VerticalLayout {

    private final BookinigService bookinigService;
    private Grid<BookingDto> bookingList = new Grid<>(BookingDto.class);

    public BookingView(BookinigService bookinigService) {
        this.bookinigService = bookinigService;
        add(bookingList);
        init();
    }

    public void init() {
        UserDto user = UserDtoMap.getInstance().getCustomerMap().get(VaadinSession.getCurrent());
        List<BookingDto> userBookings = bookinigService.getBookingsForUser(user.getId());
        bookingList.setItems(userBookings);
    }
}
