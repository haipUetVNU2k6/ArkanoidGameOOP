package com.example.arkanoidgameoop.model;

public class Brick extends GameObject {
    public boolean alive = true;
    public Brick(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}

//public class Brick extends GameObject {
//
//    public Brick(double x, double y, int width, int height) {
//        super(x, y, width, height);
//    }
//
//    @Override
//    public void render(GraphicsContext gc) {
//        gc.setFill(Color.GREEN);
//        gc.fillRect(x, y, width, height);
//        gc.setStroke(Color.BLACK);
//        gc.strokeRect(x, y, width, height);
//    }
//
//    public Rectangle2D getBounds() {
//        return new Rectangle2D(x, y, width, height);
//    }
//}