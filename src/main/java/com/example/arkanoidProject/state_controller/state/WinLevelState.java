package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.util.FireworkCanvas;
import com.example.arkanoidProject.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class WinLevelState extends State{
    public WinLevelState() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/winLevel.fxml"));
            ui = loader.load();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


// Thêm hiệu ứng pháo hoa (cách làm giống menu)
