package com.travel.agency.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
public class HomeView extends VerticalLayout {


    public HomeView() {
        Image image = new Image("https://dichvuhangkhong.com.vn/wp-content/uploads/2015/02/banner-may-bay.jpg", "gif");
        image.setHeight("530px");
        image.setWidth("1280px");
        setAlignItems(Alignment.CENTER);
        add(image);
    }
}
