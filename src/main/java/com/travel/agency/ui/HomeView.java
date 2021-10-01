package com.travel.agency.ui;

import com.travel.agency.domain.TravelDto;
import com.travel.agency.service.TravelService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "", layout = MainView.class)
public class HomeView extends VerticalLayout {

    private TravelService travelService;

    public HomeView(TravelService travelService) {
        this.travelService = travelService;
        List<TravelDto> travelDtos = travelService.getLastMinuteTrips();
        Image image = new Image("https://dichvuhangkhong.com.vn/wp-content/uploads/2015/02/banner-may-bay.jpg", "gif");
        add(image);
    }
}
