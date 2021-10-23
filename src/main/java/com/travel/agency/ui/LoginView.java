package com.travel.agency.ui;

import com.travel.agency.domain.LoginDto;
import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.service.LoginService;
import com.travel.agency.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import java.time.LocalDateTime;

@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final LoginForm loginForm = new LoginForm();
    private final LoginI18n loginI18n = LoginI18n.createDefault();
    private final LoginService loginService;
    private final UserService userService;
    private final RegisterView registerView = new RegisterView();
    private final Tab loginTab = new Tab(loginForm, registerView);
    private final static String LOGIN = "LOGGED IN";

    public LoginView(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
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
            if(userService.existByName(event.getUsername())) {
                UserDto userDto = userService.loadUserByUsername(event.getUsername());
                UserDtoMap.getInstance().addToMap(VaadinSession.getCurrent(), userDto);
                registerLoginEvent(userDto);
            }
        });
    }

    private void registerLoginEvent(UserDto userDto) {
        LoginDto loginDto = new LoginDto(LocalDateTime.now(), LOGIN, userDto.getId());
        loginService.createLoginEvent(loginDto);
    }

    private void clickRegisterNewUser() {
        loginForm.addForgotPasswordListener(event -> getUI().ifPresent(loginForm -> enableRegisterView()));
    }

    private void enableRegisterView() {
        registerView.setUserService(userService);
        registerView.setLoginForm(loginForm);
        registerView.setVisible(true);
        loginForm.setVisible(false);
    }
}
