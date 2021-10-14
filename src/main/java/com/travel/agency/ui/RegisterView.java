package com.travel.agency.ui;

import com.travel.agency.domain.UserDto;
import com.travel.agency.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Route(value = "register" )
@Getter
@Slf4j
public class RegisterView extends VerticalLayout {
    private UserService userService;
    private LoginForm loginForm;
    private Label message = new Label();
    private TextField username = new TextField("Name");
    private TextField lastname = new TextField("Lastname");
    private EmailField email = new EmailField("Email");
    private NumberField phone = new NumberField("Phone number");
    private PasswordField password = new PasswordField("Password");
    private Button submit = new Button("Register");
    private Button back = new Button("Back");

    private HorizontalLayout buttons = new HorizontalLayout();

    public RegisterView() {
        addClassName("register-form");
        back.setClassName("back-button");
        message.setText("Please enter your details and click 'submit'");
        submit.addClickShortcut(Key.ENTER);
        back.addClickShortcut(Key.ESCAPE);
        back.setMaxWidth(submit.getWidth());
        submit.addClickListener(event -> clickRegisterButton());
        back.addClickListener(event -> clickBackToLogin());
        buttons.add(submit, back);

        add(message, username, lastname, password, email, phone, buttons);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    private void clickBackToLogin() {
        this.setVisible(false);
        loginForm.setVisible(true);

    }

    private void clickRegisterButton() {
        if(userService.existByName(this.getUsername().getValue())) {
            message.setText("Customer named " + this.getUsername().getValue() +" already exists in DB");
            return;
        }
        UserDto customerToSave = new UserDto(
                this.getUsername().getValue(),
                this.getLastname().getValue(),
                this.getEmail().getValue(),
                this.getPhone().getValue().toString(),
                this.getPassword().getValue(),
                "USER"
        );
       if (userService.saveNewUser(customerToSave)) {
           message.setText("Customer created succesfully");
       }
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}
