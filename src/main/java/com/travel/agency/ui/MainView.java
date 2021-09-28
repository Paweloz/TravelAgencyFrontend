package com.travel.agency.ui;

import com.travel.agency.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


@CssImport("./styles.css")
public class MainView extends AppLayout {

    private final SecurityService securityService;



    public MainView(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 banner = new H1("Travel agency");
        banner.addClassName("banner");
        Button logout = new Button("logout", event -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), banner, logout);
        header.expand(banner);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink home = new RouterLink("Last Minute", TripsView.class);
        home.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(home,
                new RouterLink("Bookings", BookingView.class),
                new RouterLink("User Info", UserDetailsView.class),
                new RouterLink("Contact", ContactView.class)
        ));
    }
}
