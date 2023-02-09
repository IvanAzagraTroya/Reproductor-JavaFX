module com.ivan.reproductorjavafx {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
    requires javafx.media;
    requires java.net.http;
    requires org.json;

    opens com.ivan.reproductorjavafx to javafx.fxml;
    exports com.ivan.reproductorjavafx;
    exports com.ivan.reproductorjavafx.config;
    opens com.ivan.reproductorjavafx.config to javafx.fxml;
    exports com.ivan.reproductorjavafx.controller;
    opens com.ivan.reproductorjavafx.controller to javafx.fxml;
    exports com.ivan.reproductorjavafx.model;
    opens com.ivan.reproductorjavafx.model to javafx.fxml;
}