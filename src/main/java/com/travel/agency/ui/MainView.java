package com.travel.agency.ui;

import com.travel.agency.domain.LoginDto;
import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.security.SecurityService;
import com.travel.agency.service.LoginService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import java.time.LocalDateTime;


public class MainView extends AppLayout {

    private final SecurityService securityService;
    private final LoginService loginService;
    private final static String LOGOUT = "LOGGED OUT";

    public MainView(SecurityService securityService, LoginService loginService) {
        this.securityService = securityService;
        this.loginService = loginService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 banner = new H1("Find your dream trip");
        Button logout = new Button("logout", event -> createLogoutEvent());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), banner, logout);
        header.expand(banner);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink home = new RouterLink("Home", HomeView.class);
        RouterLink adminPanel = new RouterLink("Admin Panel", AdminView.class);
        home.setHighlightCondition(HighlightConditions.sameLocation());
        adminPanel.setVisible(checkUserRole());
        addToDrawer(new VerticalLayout(home,
                new RouterLink("Find Trip", TripsView.class),
                new RouterLink("Your Bookings", BookingView.class),
                new RouterLink("Edit user details", UserDetailsView.class),
                new RouterLink("Contact", ContactView.class),
                adminPanel
        ));
    }

    private boolean checkUserRole() {
        UserDto userDto = UserDtoMap.getInstance().getUserDtoByKey(VaadinSession.getCurrent());
        return userDto.getRole().equals("ADMIN");
    }

    private void createLogoutEvent() {
        UserDto userDto = UserDtoMap.getInstance().getUserDtoByKey(VaadinSession.getCurrent());
        LoginDto loginDto = new LoginDto(LocalDateTime.now(), LOGOUT, userDto.getId());
        loginService.createLoginEvent(loginDto);
        securityService.logout();
    }
}
