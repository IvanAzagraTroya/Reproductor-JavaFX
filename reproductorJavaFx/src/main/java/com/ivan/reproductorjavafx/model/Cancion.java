package com.ivan.reproductorjavafx.model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class Cancion {
    private String title;
    private File file;
    private String uri;
    private String album;
    private String artist;
    private Image image;

    public Cancion(){}
    public Cancion(String title, File file, String album, String artist) {
        this.title = title;
        this.file = file;
        this.album = album;
        this.artist = artist;
    }

    public Cancion(File file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
