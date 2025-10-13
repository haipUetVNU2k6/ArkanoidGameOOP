package com.example.arkanoidProject.state_controller.state;

import javafx.stage.Stage;

public class CurrentState {
    private State curState;

    public void setCurrentState(State state) {
        this.curState = state;
    }

    public void update() {
        curState.update();
    }

    // Sửa method render để nhận Stage làm tham số
    public void render(Stage primaryStage) throws Exception {
        curState.render(primaryStage);
    }
}
