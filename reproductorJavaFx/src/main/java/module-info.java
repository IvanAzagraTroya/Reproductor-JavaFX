module com.ivan.reproductorjavafx {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
    requires javafx.media;

    opens com.ivan.reproductorjavafx to javafx.fxml;
    exports com.ivan.reproductorjavafx;
}