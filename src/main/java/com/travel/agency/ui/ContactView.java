package com.travel.agency.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route(value = "/contact", layout = MainView.class)
public class ContactView extends VerticalLayout {

    private TextArea message = new TextArea("Your message");
    private Button sendButton = new Button("Send");

    public ContactView() {
        setSizeFull();
        add(message, sendButton);
    }
}
