package com.ivan.reproductorjavafx.controller;

import com.ivan.reproductorjavafx.model.Cancion;
import com.ivan.reproductorjavafx.config.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

public class CancionesController implements Initializable {

    private final Properties properties = Config.readProperties();
    private final String uri = properties.getProperty("class_dir");
    private final String dockerUri = properties.getProperty("docker_dir");
    private final String uric = properties.getProperty("class");
    private final String docker = properties.getProperty("docker");
    @FXML
    private ListView<Cancion> listView;
    private AppController appController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController = new AppController();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dockerUri))
                //.uri(URI.create(uri))
                .build();
        getSongsFromApi(request, client);

    }

    private void getSongsFromApi(HttpRequest request, HttpClient client) {
        Thread t = new Thread( () -> {
            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            response.thenAccept(res -> {
                        if(res.statusCode() == 200) {
                            JSONArray dataArray = new JSONArray(res.body());
                            for(int i = 0; i < dataArray.length(); i++){
                                JSONObject data = dataArray.getJSONObject(i);
                                var cancion = new Cancion();
                                cancion.setTitle(data.getString("title"));
                                cancion.setAlbum(String.valueOf(data.getJSONObject("album").get("title")));
                                cancion.setArtist(String.valueOf(data.getJSONObject("album").getJSONObject("artist").get("name")));
                                cancion.setUri(data.getString("file"));
                                cancion.setImage(new Image(docker+ data.getJSONObject("album").get("picture")));
                                listView.getItems().add(cancion);
                                System.out.println(cancion);
                            }
                        }
                    }
            ).join();
        });
        t.start();
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                System.out.println("Sending selected song");
                appController.setSongToPlay(newValue);
            }
        });
    }
}
