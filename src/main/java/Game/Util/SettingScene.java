package Game.Util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;


public class SettingScene {
    private Image settingBg;
    private Image settings;
    private Button exit;
    public SettingScene() {
        settingBg = new Image(getClass().getResource("/image/background.png").toExternalForm());

        exit = new Button("Exit", 200.1, 523.8, 199.8, 41.9);

        exit.setImgButton("/image/ball.png");
        exit.setImgHoverButton("/image/ball.png");
    }
    public void drawSettingScene(GraphicsContext render) {
        render.drawImage(settingBg,0, 0, 600, 650);
        exit.draw(render);
    }
    public void checkHover(MouseEvent e) {
        exit.setHovering(e);
    }
    public boolean exitClicked(MouseEvent e) {
        return exit.isClicked(e);
    }

    public static void main(String[] args) {

    }
}
