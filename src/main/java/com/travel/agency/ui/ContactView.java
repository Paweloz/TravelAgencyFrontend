package com.travel.agency.ui;

import com.travel.agency.domain.UserMessageDto;
import com.travel.agency.service.UserMessageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route(value = "/contact", layout = MainView.class)
public class ContactView extends VerticalLayout {

    private final UserMessageService userMessageService;
    private final Notification confirmNotification = new Notification("Messeage succesffully send", 5000);
    private final Notification failNotification = new Notification("Failed to send message", 5000);
    private TextArea message = new TextArea("Your message");
    private Button sendButton = new Button("Send");

    public ContactView(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
        message.setWidth("500px");
        message.setHeight("200px");
        message.setPlaceholder("Please insert your message here");
        sendButton.addClickListener(event -> clickSendButton());
        add(message, sendButton);
        setAlignItems(Alignment.CENTER);
    }

    private void clickSendButton() {
        UserMessageDto userMessage = userMessageService.buildMessage(message.getValue());
        userMessageService.sendMessage(userMessage);
        confirmNotification.open();
    }
}
