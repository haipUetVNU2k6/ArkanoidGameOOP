package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;

import java.util.Stack;

public class StateStack {
    private final Stack<State> stateStack = new Stack<>();

    public void push(State state) {
        stateStack.push(state);
        // Thêm giao diện vào root
        if (!MainApp.root.getChildren().contains(state.getUI())) {
            MainApp.root.getChildren().add(state.getUI());
        }

        // dòng này để lấy lại focus cho state hiện thời,
        // tránh trường hợp bị JavaFX "ăn" mất phím Space
        MainApp.scene.getRoot().requestFocus();

    }

    public void pop() {
        if (!stateStack.isEmpty()) {
            State top = stateStack.pop();
            MainApp.root.getChildren().remove(top.getUI());
        }
    }

    public void clear() {
        while (!stateStack.isEmpty()) {
            pop();
        }
    }
    public boolean isEmpty() {
        return stateStack.isEmpty();
    }

    public State peek() {
        return stateStack.peek();
    }
}
