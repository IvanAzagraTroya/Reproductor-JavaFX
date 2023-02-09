package com.ivan.reproductorjavafx.controller;

import com.ivan.reproductorjavafx.model.Cancion;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private TranslateTransition menuTransition;
    @FXML
    public Button menuButton, abrirButton, salirButton, themeButton;
    @FXML
    private VBox menuVBox;
    @FXML
    private AnchorPane mainAnchorPane, reproductorAnchorPane;

    private Boolean isClicked = false;
    public File selectedSong = null;
    private AppController controller;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new AppController();
        mainAnchorPane.setVisible(true);
        reproductorAnchorPane.setVisible(true);
    }

    @FXML
    public void onMenuClick(){
        isClicked = !isClicked;
        if(isClicked){
            menuTransition = new TranslateTransition(Duration.millis(600), menuVBox);
            menuTransition.setFromX(1000);
            menuTransition.setToX(-(menuVBox.getMaxWidth()+90));
            menuTransition.play();
        }else {
            menuTransition = new TranslateTransition(Duration.millis(600), menuVBox);
            menuTransition.setFromX(-(menuVBox.getMaxWidth()+90));
            menuTransition.setToX(1000);
            menuTransition.play();
        }
    }

    @FXML
    public void onExitButtonClick(){System.exit(0);}

    @FXML
    public void onOpenSongClick(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter aac = new FileChooser.ExtensionFilter("AAC files (*.aac)", "*.acc");
        FileChooser.ExtensionFilter aiff = new FileChooser.ExtensionFilter("AIFF files (*.iff)", "*.aiff");
        FileChooser.ExtensionFilter wav = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
        FileChooser.ExtensionFilter mp3 = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        fileChooser.setTitle("Select a song");
        fileChooser.getExtensionFilters().addAll(wav, aac, aiff, mp3);

        selectedSong = fileChooser.showOpenDialog(null);
        if (selectedSong != null){
            setVisible();
            var cancion = new Cancion(selectedSong);
            controller.setSongToPlay(cancion);
        }
    }
    @FXML
    public void onThemeButtonClicked(){
        menuButton.setStyle("-fx-background-color: #6F6FF3FF");
        abrirButton.setStyle("-fx-background-color: #6F6FF3FF");
        salirButton.setStyle("-fx-background-color: #6F6FF3FF");
        themeButton.setStyle("-fx-background-color: #6F6FF3FF");
        mainAnchorPane.setStyle("-fx-background-color: #da41da");
    }
    public void setVisible() {
        reproductorAnchorPane.setVisible(true);
    }
}
