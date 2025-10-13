package com.example.arkanoidProject.object;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

public abstract class GameObject {
    protected Node node;

    public Node getNode() {
        return node;
    }

    public double getX() {
        return node.getLayoutX();
    }

    public double getY() {
        return node.getLayoutY();
    }

    public void setX(double x) {
        node.setLayoutX(x);
    }

    public void setY(double y) {
        node.setLayoutY(y);
    }

    public boolean isColliding(GameObject other) {
        if (this.node instanceof Shape && other.node instanceof Shape) {
            Shape shape1 = (Shape) this.node;
            Shape shape2 = (Shape) other.node;
            return Shape.intersect(shape1, shape2).getBoundsInLocal().getWidth() != -1;
        }
        return false;
    }
}
