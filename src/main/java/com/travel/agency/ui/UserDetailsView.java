package com.travel.agency.ui;

import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "/user", layout = MainView.class)
public class UserDetailsView extends FormLayout {
    private final UserService userService;
    private TextField username = new TextField("Name");
    private TextField lastname = new TextField("Lastname");
    private EmailField email = new EmailField("Email");
    private NumberField phone = new NumberField("Phone number");
    private PasswordField password = new PasswordField("Password");
    private Button save = new Button("Save");

    public UserDetailsView(UserService userService) {
        this.userService = userService;

    }

    public void initValues() {
        UserDtoMap userDtoMap = UserDtoMap.getInstance();
        Long userId = userDtoMap.getCustomerMap().get(VaadinSession.getCurrent()).getId();
        UserDto userFromDb = userService.loadUserById(userId);
    }
}
