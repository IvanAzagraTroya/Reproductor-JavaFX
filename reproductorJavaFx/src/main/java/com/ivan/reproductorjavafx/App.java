package com.ivan.reproductorjavafx;

import com.ivan.reproductorjavafx.controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("properties.propiedades", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load(), 750, 450);
        stage.setTitle("ReproductorJavaFx");
//        stage.setResizable(false);
        stage.widthProperty().addListener(e -> {
            if(stage.getWidth() < 500) {
                AppController.smallLayout();
            } else {
                AppController.largeLayout();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}