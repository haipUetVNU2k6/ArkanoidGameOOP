package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import com.example.arkanoidProject.state_controller.controller.ChooseLevelCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class ChooseLevelState extends State {

    public ChooseLevelState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/example/arkanoidProject/view/fxml/chooseLevel.fxml"));
            root = loader.load();

            ChooseLevelCtrl controller = loader.getController();
            controller.init(this); // truyền state nếu cần

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
