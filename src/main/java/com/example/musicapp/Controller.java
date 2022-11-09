package com.example.musicapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import com.example.musicapp.model.Artist;
import com.example.musicapp.model.Datasource;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView<Artist> artistTable;
    
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
    
    public void createArtistsList() {
        Task<ObservableList<Artist>> task = new GetAllArtistsTask();
        artistTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }
}

class GetAllArtistsTask extends Task {
    @Override
    public ObservableList<Artist> call() {
        return FXCollections.observableArrayList(Datasource.getInstance().queryArtists(Datasource.ORDER_BY_ASC));
    }
}