package Game.Util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;


public class SettingScene {
    private Image settingBg;
    private Image settings;
    public Button exit;
    public Button playing;
    public Button restart;
    public SettingScene() {
        settingBg = new Image(getClass().getResource("/image/background.png").toExternalForm());

        playing = new Button("", 400,200, 50, 50);
        restart = new Button("",400,300,50,50);
        exit = new Button("",405,400,50,50);

        restart.setImgButton("/image/restart.png");
        restart.setImgHoverButton("/image/restart.png");

        playing.setImgButton("/image/Start.png");
        playing.setImgHoverButton("/image/Start.png");

        exit.setImgButton("/image/exit.png");
        exit.setImgHoverButton("/image/exit.png");
    }
    public void drawSettingScene(GraphicsContext render) {
        render.drawImage(settingBg,0, 0, 800, 600);
        playing.draw(render);
        restart.draw(render);
        exit.draw(render);
    }
    public void checkHover(MouseEvent e) {
        playing.setHovering(e);
        restart.setHovering(e);
        exit.setHovering(e);
    }
    public boolean exitClicked(MouseEvent e) {
        return exit.isClicked(e);
    }

    public boolean playClicked(MouseEvent e) {
        return playing.isClicked(e);
    }

    public boolean restartClicked(MouseEvent e) {
        return restart.isClicked(e);
    }
    public static void main(String[] args) {

    }
}
