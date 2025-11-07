package com.example.arkanoidProject.state_controller.state;

import com.example.arkanoidProject.MainApp;

import java.util.Stack;

public class StateStack {
    private final Stack<State> stateStack = new Stack<>();

    public void push(State state) {
        stateStack.push(state);
        state.onEnter(); // gọi onEnter cho state mới

        // Lấy lại focus để nhận key events
        MainApp.scene.getRoot().requestFocus();
    }

    public void pop() {
        if (!stateStack.isEmpty()) {
            State top = stateStack.pop();
            top.onExit(); // gọi onExit trước khi remove
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
