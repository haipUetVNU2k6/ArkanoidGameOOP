package Game.Util;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MenuScene {
    private Image background;
    private Image logo;
    public Button start;
    public Button settings;
    public Button exit;
    public MenuScene() {
        background = new Image(getClass().getResource("/image/background.jpg").toExternalForm());
        logo = new Image(getClass().getResource("/image/logo.png").toExternalForm());

        start = new Button("", 380, 200,70, 70);
        start.setImgButton("/image/Start.png");
        start.setImgHoverButton("/image/Start.png");

        settings = new Button("", 380, 350, 70, 70);
        settings.setImgButton("/image/setting.png");
        settings.setImgHoverButton("/image/setting.png");

        exit = new Button("", 390, 450, 65, 65);
        exit.setImgButton("/image/exit.png");
        exit.setImgHoverButton("/image/exit.png");
    }
    public void drawMenuScene(GraphicsContext render) {
        render.drawImage(background, 0, 0, 800, 600);
        render.drawImage(logo, 200, 50, 400, 150);
        start.draw(render);
        settings.draw(render);
        exit.draw(render);
    }
    public void checkHover(MouseEvent e) {
        start.setHovering(e);
        settings.setHovering(e);
        exit.setHovering(e);
    }
    public boolean settingClick(MouseEvent e) {
        return settings.isClicked(e);
    }
    public boolean exitClick(MouseEvent e) {
        return exit.isClicked(e);
    }

    public static void main(String[] args) {

    }
}


