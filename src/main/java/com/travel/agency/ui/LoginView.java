package com.travel.agency.ui;

import com.travel.agency.domain.CustomerDto;
import com.travel.agency.domain.CustomerDtoMap;
import com.travel.agency.service.CustomerService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final LoginForm loginForm = new LoginForm();
    private final LoginI18n loginI18n = LoginI18n.createDefault();
    private final CustomerService customerService;
    private final RegisterView registerView = new RegisterView();
    private final Tab loginTab = new Tab(loginForm, registerView);

    public LoginView(CustomerService customerService) {
        this.customerService = customerService;
        H1 header = new H1("Welcome to Travel Agency");
        add(header, loginTab);
        buildForm();
        clickLoginButton();
        clickRegisterNewUser();
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
        registerView.setVisible(false);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        addClickShortcut(Key.ENTER);
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

    private void clickRegisterNewUser() {
        loginForm.addForgotPasswordListener(event -> getUI().ifPresent(loginForm -> enableRegisterView()));
    }

    private void enableRegisterView() {
        registerView.setCustomerService(customerService);
        registerView.setLoginForm(loginForm);
        registerView.setVisible(true);
        loginForm.setVisible(false);
    }
}
