module ArkanoidGameOOP {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires java.desktop;



    opens Game to javafx.graphics;
    opens Game.View to javafx.graphics;
    opens Game.Util to javafx.graphics;
}
