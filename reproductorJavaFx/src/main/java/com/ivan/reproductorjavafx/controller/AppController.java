package com.ivan.reproductorjavafx.controller;

import com.ivan.reproductorjavafx.model.Cancion;
import com.ivan.reproductorjavafx.config.Config;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private final Properties properties = Config.readProperties();
    private final String uri = properties.getProperty("class");
    private final String docker = properties.getProperty("docker");
    @FXML
    private static AnchorPane anchor;
    @FXML
    private Label nombreCancionLabel, nombreGrupoLabel, nombreAlbumLabel;
    @FXML
    private Slider duracionSlider;
    @FXML
    private static Button playButton,  nextButton,  lastButton;
    @FXML
    private final Image playImage, stopImage;
    @FXML
    private ImageView mainButtonImage;
    @FXML
    private static ImageView songImage;
    @FXML
    private static HBox hBox;
    @FXML
    private static VBox vBox;

    private Media mediaFile;
    private static MediaPlayer mediaPlayer;
    private Boolean playing = false;

    private static ArrayList<Cancion> loadedFiles;
    private int num;


    public AppController() {
        loadedFiles = new ArrayList<>();
        nombreGrupoLabel = new Label();
        nombreAlbumLabel = new Label();
        nombreCancionLabel = new Label();

//        Para el cambio de imagen entre play y pause
        playImage = new Image(System.getProperty("user.dir")+File.separator+"src/main/resources/com/ivan/icons/pause.png");
        stopImage = new Image(System.getProperty("user.dir")+File.separator+"src/main/resources/com/ivan/icons/play.png");

        playButton = new Button();
        nextButton = new Button();
        lastButton = new Button();
        songImage = new ImageView();
        mainButtonImage = new ImageView();

        duracionSlider = new Slider();

        hBox = new HBox();
        hBox.setSpacing(8);


        vBox = new VBox();
        vBox.setSpacing(8);

        anchor = new AnchorPane(vBox);
        anchor.setPadding(new Insets(24));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!loadedFiles.isEmpty()){
            duracionSlider.setValue(mediaPlayer.currentCountProperty().doubleValue());
            anchor.setVisible(true);
//        cambio por dónde se encuentra la canción con el valor del slider al moverlo
            duracionSlider.valueProperty().addListener(
                    (observable, oldValue, newValue) ->
                            mediaPlayer.seek(Duration.seconds(newValue.doubleValue()))
            );
        }
    }
    //http://192.168.16.10
    public void setSongToPlay(Cancion file) {
        System.out.println("Song received");
        if(!loadedFiles.contains(file))
            loadedFiles.add(file);

        if(mediaPlayer != null) mediaPlayer.dispose();

        mediaFile = new Media(docker+file.getUri());
        mediaPlayer = new MediaPlayer(mediaFile);
        System.out.println("Canciones cargadas en memoria: "+loadedFiles.size());
        mediaPlayer.play();
    }
    @FXML
    public void onPlayButtonClick() {
        System.out.println("Canciones cargadas en memoria: "+loadedFiles.size());
        if(!loadedFiles.isEmpty()){
            playing = !playing;
            System.out.println("playing? ->" +playing);
            if(playing) {
                mediaPlayer.play();
                mediaPlayer.autoPlayProperty().set(true);
                mainButtonImage.setImage(playImage);
            }else{
                mediaPlayer.pause();
                mainButtonImage.setImage(stopImage);
            }
        }
    }

    @FXML
    public void onNextButtonClick() {
        if(!loadedFiles.isEmpty()){
            mediaPlayer.stop();
            if(num < loadedFiles.size()-1) {
                if(!playing) playing = true;
                num++;
                System.out.println("Pasando a siguiente canción");
                setLabelData(loadedFiles.get(num));

                duracionSlider.valueProperty().addListener(
                        (observable, oldValue, newValue) ->
                                mediaPlayer.seek(Duration.seconds(newValue.doubleValue()))
                );
            }else{
                System.out.println("No hay más canciones que cargar");
                num = 0;
            }

            setOtherSong();
        }
    }

    private void setOtherSong() {
        mediaFile = new Media(docker+loadedFiles.get(num).getUri());
        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(mediaFile);
        duracionSlider.setValue(0.0);
        mediaPlayer.seek(Duration.seconds(duracionSlider.valueProperty().doubleValue()));
        System.out.println("Playing canción número: "+num);
        mediaPlayer.play();
    }

    @FXML
    public void onLastButtonClick() {
        if(!loadedFiles.isEmpty()){
            mediaPlayer.stop();
            if(num > 0) {
                if(!playing) playing = true;
                System.out.println("Reproduciendo anterior canción");
                num--;
                setLabelData(loadedFiles.get(num));
                duracionSlider.valueProperty().addListener(
                        (observable, oldValue, newValue) ->
                                mediaPlayer.seek(Duration.seconds(newValue.doubleValue()))
                );
            }else {
                System.out.println("Reproduciendo última canción cargada");
                num = loadedFiles.size()-1;
            }
            setOtherSong();
        }
    }

    private void setLabelData(Cancion file) {
        nombreCancionLabel.setText(file.getTitle());
        nombreAlbumLabel.setText(file.getAlbum());
        nombreGrupoLabel.setText(file.getArtist());
        songImage.setImage(file.getImage());
    }

    //    Debería cambiar la organización de los elementos al cambiar el tamaño a través del escuchador de App
    //    pero no va
    public static void smallLayout() {
        hBox.getChildren().clear();
        vBox.getChildren().clear();
        vBox.getChildren().addAll(playButton, nextButton, lastButton, songImage);
        anchor.getChildren().clear();
        anchor.getChildren().add(vBox);
    }

    public static void largeLayout() {
        vBox.getChildren().clear();
        hBox.getChildren().clear();
        hBox.getChildren().addAll(playButton, nextButton, lastButton, songImage);
        anchor.getChildren().clear();
        anchor.getChildren().add(hBox);
    }
}