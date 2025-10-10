module Game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;

    // mở cho JavaFX có thể load FXML và truy cập controller
    opens com.example.arkanoidgameoop.controller to javafx.fxml;
    opens com.example.arkanoidgameoop.model to javafx.fxml;

    // export package cho bên ngoài dùng
    exports com.example.arkanoidgameoop;
    exports com.example.arkanoidgameoop.controller;
    exports com.example.arkanoidgameoop.model;
}
