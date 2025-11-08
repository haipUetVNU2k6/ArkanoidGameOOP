    package com.example.arkanoidProject.state_controller.state;

    import com.example.arkanoidProject.state_controller.controller.MenuCtrl;
    import com.example.arkanoidProject.util.Config;
    import com.example.arkanoidProject.util.WelcomeText;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.canvas.GraphicsContext;

    import java.io.IOException;

    public class MenuState extends State {
        WelcomeText menu_helloText;

        private GraphicsContext gc;
        private long lastTime = 0;

        private MenuCtrl controller;

        public MenuState() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoidProject/view/fxml/menu.fxml"));
                ui = loader.load();
                controller = loader.getController();
                gc = controller.getMenuCanvas().getGraphicsContext2D();
             } catch (IOException e) {
                e.printStackTrace();
            }


            menu_helloText = new WelcomeText(240, 250, -10);
        }

        @Override
        public void update() {
            long now = System.nanoTime();
            if (lastTime == 0) {
                lastTime = now;
                return;
            }
            double dt = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            menu_helloText.update(dt);

        }

        @Override
        public void render() {
            gc.clearRect(0, 0, Config.getScreenWidth(), Config.getScreenHeight());
            menu_helloText.render(gc);
        }

    }

