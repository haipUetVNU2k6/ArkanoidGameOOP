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
    requires javafx.base;
    requires java.desktop;
    requires jdk.compiler;
    requires com.google.gson;
    requires javafx.media;
//    requires Game;
//    requires Game;
//    requires Game;
//    requires Game; // <--- thêm dòng này
//    requires Game;
//    requires Game;
//    requires Game;
//    requires Game;
//    requires Game;
    //requires Game;

    // Mở cho JavaFX có thể load FXML và truy cập controller
    opens com.example.arkanoidProject.state_controller.controller to javafx.fxml;
    opens com.example.arkanoidProject.object to javafx.fxml;
    opens com.example.arkanoidProject.userAccount to com.google.gson;



    // Export package cho bên ngoài dùng
    exports com.example.arkanoidProject;
    exports com.example.arkanoidProject.state_controller.controller;
    exports com.example.arkanoidProject.state_controller.state;
    exports com.example.arkanoidProject.object;
}
