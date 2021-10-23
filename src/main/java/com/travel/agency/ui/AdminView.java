package com.travel.agency.ui;

import com.travel.agency.domain.AppProblemDto;
import com.travel.agency.service.AppProblemService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "/admin", layout = MainView.class)
public class AdminView extends VerticalLayout {

    private Grid<AppProblemDto> problemsGrid = new Grid<>(AppProblemDto.class);
    private final AppProblemService appProblemService;

    public AdminView(AppProblemService appProblemService) {
        this.appProblemService = appProblemService;
        setView();
        add(problemsGrid);
    }

    private void setView() {
        List<AppProblemDto> problemsToDisplay = appProblemService.getProblems();
        problemsGrid.setItems(problemsToDisplay);
    }
}
