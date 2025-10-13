package com.example.arkanoidProject;

import com.example.arkanoidProject.state_controller.state.CurrentState;
import com.example.arkanoidProject.state_controller.state.MenuState;
import com.example.arkanoidProject.state_controller.state.PlayState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MenuState ms = new MenuState();
        PlayState ps = new PlayState();

        CurrentState cs = new CurrentState();
        //cs.setCurState(ms);


//        while (true) {
//            System.out.println("Bạn muốn chuyển sang trạng thái nào?");
//            String input = sc.nextLine();
//            if (input.equals("menu")) {
//                cs.setCurState(ms);
//            } else if (input.equals("play")) {
//                cs.setCurState(ps);
//            } else if (input.equals("exit")) {
//                System.exit(0);
//            } else {
//                System.out.println("Hãy nhập 1 trong 3: menu - play - exit");
//                continue;
//            }
//            cs.update();
//            cs.render();
//        }
    }
}