package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuState extends State {
    public MenuState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/menu.fxml"));
            ui = loader.load();
         } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void handleKeyPressed(KeyEvent event) {
//        if (event.getCode() == KeyCode.ENTER) {
//            System.out.println("MenuState: Nhấn Enter để Play");
//            // Chuyển sang PlayState
//            MainApp.stateStack.pop();
//            MainApp.stateStack.push(new PlayState());
//        } else if (event.getCode() == KeyCode.ESCAPE) {
//            System.out.println("MenuState: Thoát game");
//            System.exit(0);
//        }
//    }
}

