package com.example.musicapp.model;

import javafx.beans.property.SimpleStringProperty;

public class SongDetail {
    private SimpleStringProperty title;
    private SimpleStringProperty artist;
    private SimpleStringProperty album;
    
    public SongDetail() {
        title = new SimpleStringProperty();
        artist = new SimpleStringProperty();
        album = new SimpleStringProperty();
    }
    
    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public void setArtist(String artist) {
        this.artist.set(artist);
    }
    
    public String getArtist() {
        return artist.get();
    }
    
    public void setAlbum(String album) {
        this.album.set(album);
    }
    
    public String getAlbum() {
        return album.get();
    }
}
