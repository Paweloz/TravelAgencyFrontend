package com.travel.agency.ui;

import com.travel.agency.domain.CustomerDto;
import com.travel.agency.domain.CustomerDtoMap;
import com.travel.agency.service.CustomerService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final LoginForm loginForm = new LoginForm();
    private final LoginI18n loginI18n = LoginI18n.createDefault();
    private final CustomerService customerService;

    public LoginView(CustomerService customerService) {
        this.customerService = customerService;
        buildForm();
        clickLoginButton();
        H1 header = new H1("Welcome to Travel Agency");
        add(header, loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }

    private void buildForm() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        loginI18n.getForm().setForgotPassword("Register new user");
        loginForm.setI18n(loginI18n);
        loginForm.setAction("login");
    }

    private void clickLoginButton() {
        loginForm.addLoginListener(event -> {
            if(customerService.existByName(event.getUsername())) {
                CustomerDto customerDto = customerService.loadUserByUsername(event.getUsername());
                CustomerDtoMap.getInstance().addToMap(VaadinSession.getCurrent(), customerDto);
            }
        });
    }
}
