package com.example.musicapp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty track;
    private SimpleStringProperty name;
    private SimpleIntegerProperty albumId;
    
    public Song() {
        id = new SimpleIntegerProperty();
        track = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        albumId = new SimpleIntegerProperty();
    }
    
    public int getId() {
        return id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String title) {
        this.name.set(title);
    }
    
    public int getTrack() {
        return track.get();
    }
    
    public void setTrack(int track) {
        this.track.set(track);
    }
    
    public int getAlbumId() {
        return albumId.get();
    }
    
    public void setAlbumId(int albumId) {
        this.albumId.set(albumId);
    }
}
