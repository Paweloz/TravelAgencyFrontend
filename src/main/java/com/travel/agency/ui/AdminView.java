package com.travel.agency.ui;

import com.travel.agency.domain.AppProblemDto;
import com.travel.agency.domain.UserDto;
import com.travel.agency.service.AppProblemService;
import com.travel.agency.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "/admin", layout = MainView.class)
public class AdminView extends VerticalLayout {

    private Grid<AppProblemDto> problemsGrid = new Grid<>(AppProblemDto.class);
    private Grid<UserDto> usersGrid = new Grid<>(UserDto.class);
    private final AppProblemService appProblemService;
    private final UserService userService;
    private VerticalLayout pageView = new VerticalLayout(problemsGrid, usersGrid);

    public AdminView(AppProblemService appProblemService, UserService userService) {
        this.appProblemService = appProblemService;
        this.userService = userService;
        setProblemsView();
        setUserView();
        add(pageView);
    }

    private void setProblemsView() {
        List<AppProblemDto> problemsToDisplay = appProblemService.getProblems();
        problemsGrid.setItems(problemsToDisplay);
        problemsGrid.addComponentColumn(item -> createRemoveButton(problemsGrid, item)).setHeader("Action");
    }

    private void setUserView() {
        List<UserDto> userToDisplay = userService.getAllUsers();
        removeAdmin(userToDisplay);
        usersGrid.setItems(userToDisplay);
        usersGrid.setColumns("id", "username", "lastname", "email", "phone", "role");
        usersGrid.addComponentColumn(item -> createRemoveButton(usersGrid, item)).setHeader("Action");
    }

    private Button createRemoveButton(Grid<AppProblemDto> appProblemsGrid, AppProblemDto appProblemDto) {
        Button removeButton = new Button("remove");
        removeButton.addClickListener(event -> {
            appProblemService.removeProblem(appProblemDto.getId());
            appProblemsGrid.setItems(appProblemService.getProblems());
        });
        return removeButton;
    }

    private Button createRemoveButton(Grid<UserDto> usersGrid, UserDto userDto) {
        Button removeButton = new Button("remove");
        removeButton.addClickListener(event -> {
            userService.removeUserById(userDto.getId());
            List<UserDto> reDisplay = userService.getAllUsers();
            removeAdmin(reDisplay);
            usersGrid.setItems(reDisplay);
        });
        return removeButton;
    }

    private void removeAdmin(List<UserDto> userToDisplay) {
        userToDisplay.removeIf(userDto -> userDto.getRole().equals("ADMIN"));
    }
}
