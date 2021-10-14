package com.travel.agency.ui;

import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.service.UserService;
import com.travel.agency.validator.Validator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "/user", layout = MainView.class)
public class UserDetailsView extends FormLayout {
    private final UserService userService;
    private final Validator validator;
    private TextField username = new TextField("Name");
    private TextField lastname = new TextField("Lastname");
    private EmailField email = new EmailField("Email");
    private NumberField phone = new NumberField("Phone number");
    private PasswordField password = new PasswordField("Password");
    private Button save = new Button("Save");

    public UserDetailsView(UserService userService, Validator validator) {
        this.validator = validator;
        this.userService = userService;
        init();
    }

    public void init() {
        UserDto userFromDb = getCurrentUser();
        setFields(userFromDb);
        save.addClickListener(event -> clickSaveButton(userFromDb));
        add(username, lastname, email, phone, password, save);
    }

    private void setFields(UserDto userFromDb) {
        username.setValue(userFromDb.getUsername());
        username.setEnabled(false);
        lastname.setValue(userFromDb.getLastname());
        lastname.setEnabled(false);
        email.setValue(userFromDb.getEmail());
        phone.setValue(Double.parseDouble(userFromDb.getPhone().trim()));
        password.setPlaceholder("Enter new password");
    }

    private UserDto getCurrentUser() {
        UserDtoMap userDtoMap = UserDtoMap.getInstance();
        Long userId = userDtoMap.getCustomerMap().get(VaadinSession.getCurrent()).getId();
        return userService.loadUserById(userId);
    }

    public void clickSaveButton(UserDto userDto) {
        userDto.setEmail(email.getValue());
        userDto.setPhone(phone.getValue().toString());
        userDto.setPassword(password.getValue());
        if(validator.validateUserEdition(userDto)) {
            userService.saveEditedUser(userDto);
            return;
        }
        //here gonna be some fail notification code
    }

}
