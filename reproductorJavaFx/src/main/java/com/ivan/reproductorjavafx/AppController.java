package com.ivan.reproductorjavafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * He precargado la misma canción porque no tenía otra y me daba pereza
 */
public class AppController implements Initializable {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label nombreCancionLabel, nombreGrupoLabel, nombreAlbumLabel;
    @FXML
    private Slider duracionSlider;
//    @FXML
//    private Button playButton, nextButton, lastButton;
    @FXML
    private Image playImage, stopImage;
    @FXML
    private ImageView mainButtonImage, songImage;

    private Media mediaFile;
    private MediaPlayer mediaPlayer;// = new MediaPlayer(mediaFile);
    private Boolean playing = false;

    private ArrayList<File> loadedFiles = new ArrayList();
    private int num;

    public AppController() {
        // Carga de canciones para comprobar que ejecuta las acciones correctamente
        File f1 = new File("src/main/resources/com/ivan/SuperGrottoEscape.wav");
        File f2 = new File("src/main/resources/com/ivan/SuperGrottoEscape.wav");
        File f3 = new File("src/main/resources/com/ivan/SuperGrottoEscape.wav");
        File f4 = new File("src/main/resources/com/ivan/SuperGrottoEscape.wav");
        loadedFiles.add(f1);
        loadedFiles.add(f2);
        loadedFiles.add(f3);
        loadedFiles.add(f4);
//        Creo el primer media player que ejecutará la primera canción cargada
        mediaFile = new Media(loadedFiles.get(0).toURI().toString());
        mediaPlayer = new MediaPlayer(mediaFile);
//        Para el cambio de imagen entre play y pause
        playImage = new Image(System.getProperty("user.dir")+File.separator+"src/main/resources/com/ivan/icons/pause.png");
        stopImage = new Image(System.getProperty("user.dir")+File.separator+"src/main/resources/com/ivan/icons/play.png");
//        Obtengo los metadatos para actualizar los labels (la canción de ejemplo no contiene metadatos)
        mediaFile.getMetadata().addListener((MapChangeListener.Change<? extends String, ? > change) -> {
            System.out.println(change.getKey());
            switch(change.getKey().toString()) {
                case "title" -> nombreCancionLabel.setText(change.getValueAdded().toString());
                case "album" -> nombreAlbumLabel.setText(change.getValueAdded().toString());
                case "artist" -> nombreGrupoLabel.setText(change.getValueAdded().toString());
                case "image" -> songImage.setImage((Image) change.getValueAdded());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        duracionSlider.valueProperty().bind(mediaPlayer.currentCountProperty());
        duracionSlider.setValue(mediaPlayer.currentCountProperty().doubleValue());
        anchor.setVisible(true);
//        cambio por dónde se encuentra la canción con el valor del slider al moverlo
        duracionSlider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        mediaPlayer.seek(Duration.seconds(newValue.doubleValue()))
        );
    }

    @FXML
    public void onPlayButtonClick() {
        playing = !playing;
        System.out.println("playing? ->" +playing);
        if(playing) {
            mediaPlayer.play();
            mainButtonImage.setImage(playImage);
        }else{
            mediaPlayer.pause();
            mainButtonImage.setImage(stopImage);
        }
    }

    @FXML
    public void onNextButtonClick(MouseEvent event) {
        if(num < loadedFiles.size()-1) {
            num++;
            System.out.println("Pasando a siguiente canción");
        }else{
            System.out.println("No hay más canciones que cargar");
            num = 0;
        }
        mediaFile = new Media(loadedFiles.get(num).toURI().toString());
        duracionSlider.setValue(0.0);
        mediaPlayer.seek(Duration.seconds(duracionSlider.valueProperty().doubleValue()));
        System.out.println("Playing canción número: "+num);
    }

    @FXML
    public void onLastButtonClick(MouseEvent event) {
        if(num > 0) {
            System.out.println("Reproduciendo anterior canción");
            num--;
        }else {
            System.out.println("Reproduciendo última canción cargada");
            num = loadedFiles.size()-1;
        }
        mediaFile = new Media(loadedFiles.get(num).toURI().toString());
        duracionSlider.setValue(0.0);
        duracionSlider.setValue(0.0);
        mediaPlayer.seek(Duration.seconds(duracionSlider.valueProperty().doubleValue()));
        System.out.println("Playing canción número: "+num);
    }

}