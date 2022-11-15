package com.example.musicapp;

import com.example.musicapp.model.Album;
import com.example.musicapp.model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.musicapp.model.Artist;
import com.example.musicapp.model.Datasource;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Const;
import util.Util;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView artistTable;
    @FXML
    private ProgressBar progressBar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Datasource.getInstance().insertSong("Bad Habits", "Ed Sheeran", "=", 4);
//        Datasource.getInstance().insertSong("Overpass Graffiti", "Ed Sheeran", "=", 5);
//        Datasource.getInstance().insertSong("Photograph", "Ed Sheeran", "x", 6);
//        Datasource.getInstance().insertSong("In the name of love", "Martin Garrix", "Sentio", 1);
//        Datasource.getInstance().insertSong("Closer", "The Chainsmokers", "Memories... Do Not Open", 13);
//        Datasource.getInstance().insertSong("Something Just Like This", "The Chainsmokers", "Memories... Do Not Open", 5);
//        Datasource.getInstance().insertSong("Bad Liar", "Imagine Dragons", "Origins", 5);
//        Datasource.getInstance().insertSong("Believer", "Imagine Dragons", "Evolve", 4);
//        Datasource.getInstance().insertSong("Feel So Close", "Calvin Harris", "18 Months", 3);
//        Datasource.getInstance().insertSong("Summer", "Calvin Harris", "Motion", 8);
//        Datasource.getInstance().insertSong("Waiting For Love", "Avicii", "Stories", 1);
//        Datasource.getInstance().insertSong("Wake Me Up", "Avicii", "True", 1);
//        Datasource.getInstance().insertSong("Past Lives", "Martin Arteta", "Past Lives", 1);
//        Datasource.getInstance().insertSong("Thorn in My Side", "Nik Ammar", "Pop Noir", 12);
//        Datasource.getInstance().insertSong("Let Her Go", "Passenger", "All the Little Lights", 2);
//        Datasource.getInstance().insertSong("Hello", "Adele", "25", 1);
//        Datasource.getInstance().insertSong("Let Me Down Slowly", "Alec Benjamin", "Narrated For You", 4);
//        Datasource.getInstance().insertSong("Water Fountain", "Alec Benjamin", "Narrated For You", 2);
//        Datasource.getInstance().insertSong("Em dạo này", "Ngọt", "Ng'bthg", 7);
//        Datasource.getInstance().insertSong("Circles", "Post Malone", "Hollywood's Bleeding", 6);
//        Datasource.getInstance().insertSong("Sunflower - Spider-man: Into the Spider-Verse", "Post Malone", "Hollywood's Bleeding", 12);
//        Datasource.getInstance().insertSong("Old Town Road", "Lil Nas X", "7 EP", 8);
//        Datasource.getInstance().insertSong("A Sky Full of Stars", "Coldplay", "Ghost Stories", 8);
//        Datasource.getInstance().insertSong("all the kids are depressed", "Jeremy Zucker", "glisten", 1);
//        Datasource.getInstance().insertSong("Never Enough", "Loren Allred", "The Greatest Showman", 6);
//        Datasource.getInstance().insertSong("Is There Still Anything That Love Can Do?", "RADWIMPS", "Weathering With You", 31);


//        List<Artist> list =  Datasource.getInstance().queryArtists(Datasource.ORDER_BY_NONE);
//        for (Artist a : list) {
//            System.out.println(a.getId().intValue() + ": " + a.getName().getValue());
//        }
    
    }
    
    @FXML
    public void listArtists() {
        Task<ObservableList<Artist>> task = new GetAllArtistsTask();
        artistTable.itemsProperty().bind(task.valueProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));
        new Thread(task).start();
    }
    
    @FXML
    public void listAlbums() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Artist artist)) return;
        Task<ObservableList<Album>> task = new Task<>() {
            @Override
            protected ObservableList<Album> call() {
            return FXCollections.observableArrayList(Datasource.getInstance().queryAlbumsByArtistID(artist.getId()));
            }
        };
        artistTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }
    
    @FXML
    public void listSongs() {
        Task<ObservableList<Song>> task = null;
        if (artistTable.getSelectionModel().getSelectedItem() instanceof final Album album) {
            task = new Task<>() {
                @Override
                protected ObservableList<Song> call() {
                    return FXCollections.observableArrayList(Datasource.getInstance().querySongsByAlbumID(album.getId()));
                }
            };
        } else if (artistTable.getSelectionModel().getSelectedItem() instanceof final Artist artist) {
            task = new Task<>() {
                @Override
                protected ObservableList<Song> call() {
                    return FXCollections.observableArrayList(Datasource.getInstance().querySongByArtist(artist.getId()));
                }
            };
        } else return;
        artistTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }
    
    @FXML
    public void updateArtist() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Artist artist)) return;
        TextInputDialog input = new TextInputDialog();
        input.setTitle("Change artist's name");
        input.setHeaderText("Enter artist's new name: ");
        input.showAndWait();
        String newName = input.getResult();
        
        if (newName == null || newName.equals("")) return;
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
            return Datasource.getInstance().updateArtist(artist.getId(), newName);
            }
        };
        task.setOnSucceeded(e -> {
            if (task.valueProperty().get()) {
                artist.setName(newName);
                artistTable.refresh();
            }
        });
        new Thread(task).start();
    }
    
    @FXML
    public void updateAlbum() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Album album)) return;
        TextInputDialog input = new TextInputDialog();
        input.setTitle("Change album's name");
        input.setHeaderText("Enter album's new name: ");
        input.showAndWait();
        String newName = input.getResult();
        if (newName == null || newName.equals("")) return;
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
            return Datasource.getInstance().updateAlbum(album.getId(), newName);
            }
        };
        task.setOnSucceeded(e -> {
            if (task.valueProperty().get()) {
                album.setName(newName);
                artistTable.refresh();
            }
        });
        new Thread(task).start();
    }
    
    @FXML
    public void updateSong() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Song song)) return;
        TextInputDialog input = new TextInputDialog();
        input.setTitle("Change song's name");
        input.setHeaderText("Enter song's new name: ");
        input.showAndWait();
        String newName = input.getResult();
        if (newName == null || newName.equals("")) return;
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return Datasource.getInstance().updateSong(song.getId(), newName);
            }
        };
        task.setOnSucceeded(e -> {
            if (task.valueProperty().get()) {
                song.setName(newName);
                artistTable.refresh();
            }
        });
        new Thread(task).start();
    }
    
    @FXML
    public void insertSong() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setMinSize(400, 150);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        VBox col1 = new VBox();
        Label name = new Label("Name: ");
        name.setMinSize(Const.LABEL_WIDTH, Const.LABEL_HEIGHT);
        Label album = new Label("Album: ");
        album.setMinSize(Const.LABEL_WIDTH, Const.LABEL_HEIGHT);
        Label artist = new Label("Artist: ");
        artist.setMinSize(Const.LABEL_WIDTH, Const.LABEL_HEIGHT);
        Label track = new Label("Track: ");
        track.setMinSize(Const.LABEL_WIDTH, Const.LABEL_HEIGHT);
        col1.getChildren().add(name);
        col1.getChildren().add(album);
        col1.getChildren().add(artist);
        col1.getChildren().add(track);
        
        VBox col2 = new VBox();
        TextField nameField = new TextField();
        nameField.setMinSize(Const.FIELD_WIDTH, Const.FIELD_HEIGHT);
        TextField albumField = new TextField();
        albumField.setMinSize(Const.FIELD_WIDTH, Const.FIELD_HEIGHT);
        TextField artistField = new TextField();
        artistField.setMinSize(Const.FIELD_WIDTH, Const.FIELD_HEIGHT);
        TextField trackField = new TextField();
        trackField.setMinSize(Const.FIELD_WIDTH, Const.FIELD_HEIGHT);
        col2.getChildren().add(nameField);
        col2.getChildren().add(albumField);
        col2.getChildren().add(artistField);
        col2.getChildren().add(trackField);
    
        HBox box = new HBox();
        box.getChildren().add(col1);
        box.getChildren().add(col2);
        dialog.getDialogPane().getChildren().add(box);
        
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
            return;
        }
        String songName = nameField.getText();
        String albumName = albumField.getText();
        String artistName = artistField.getText();
        String trackSong = trackField.getText();
        if (!Util.isNumeric(trackSong) || songName.equals("")
        || albumName.equals("") || artistName.equals("")) return;
        int songTrack = Integer.parseInt(trackField.getText());
        Datasource.getInstance().insertSong(songName, artistName, albumName, songTrack);
    }
    
    @FXML
    public void deleteArtist() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Artist artist)) return;
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return Datasource.getInstance().deleteArtist(artist.getId());
            }
        };
        task.setOnSucceeded(e -> {
            artistTable.getItems().remove(artist);
            artistTable.refresh();
        });
        new Thread(task).start();
    }
    
    @FXML
    public void deleteAlbum() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Album album)) return;
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return Datasource.getInstance().deleteAlbum(album.getId());
            }
        };
        task.setOnSucceeded(e -> {
            artistTable.getItems().remove(album);
            artistTable.refresh();
        });
        new Thread(task).start();
    }
    
    @FXML
    public void deleteSong() {
        if (!(artistTable.getSelectionModel().getSelectedItem() instanceof final Song song)) return;
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return Datasource.getInstance().deleteSong(song.getId());
            }
        };
        task.setOnSucceeded(e -> {
            artistTable.getItems().remove(song);
            artistTable.refresh();
        });
        new Thread(task).start();
        
    }
}

class GetAllArtistsTask extends Task<ObservableList<Artist>> {
    @Override
    public ObservableList<Artist> call() {
        return FXCollections.observableArrayList(Datasource.getInstance().queryArtists(Datasource.ORDER_BY_ASC));
    }
}