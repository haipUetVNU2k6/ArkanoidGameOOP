package com.example.arkanoidProject.state_controller.state;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayState extends State {
    private Pane root;

    public PlayState() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    PlayState.class.getResource("/com/example/arkanoidProject/fxml/play.fxml")
            );
            root = loader.load();
            root.getStylesheets().add(getClass().getResource("/com/example/arkanoidProject/css/style.css").toExternalForm());
            root.getStylesheets().add(getClass().getResource("/com/example/arkanoidProject/css/play.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            System.out.println("PlayState: Di chuyển trái");
            // Di chuyển thanh paddle trái
        } else if (event.getCode() == KeyCode.RIGHT) {
            System.out.println("PlayState: Di chuyển phải");
            // Di chuyển thanh paddle phải
        } else if (event.getCode() == KeyCode.P) {
            System.out.println("PlayState: Pause game");
            // Đẩy PauseState vào stack, ví dụ
            // MainApp.stateStack.push(new PauseState());
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            System.out.println("PlayState: Dừng di chuyển");
            // Dừng paddle di chuyển
        }
    }


    @Override
    public void update() {
    }

    @Override
    public void render() {
    }

    @Override
    public Pane getUI() {
        return root;
    }
}
