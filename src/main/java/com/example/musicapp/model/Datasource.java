package com.example.musicapp.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String DB_NAME = "music";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
    // albums table
    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST_ID = "artistId";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;
    // artists table
    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;
    // songs table
    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM_ID = "albumId";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;
    // order by const
    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;
    // queries
    public static final String QUERY_ALBUMS_BY_ARTIST_ID = "SELECT * FROM "
            + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ARTIST_ID + " = ?";
    public static final String QUERY_SONG_BY_ALBUM_ID = "SELECT * FROM " +
            TABLE_SONGS + " WHERE " + COLUMN_SONG_ALBUM_ID + " = ?";
    public static final String QUERY_ARTIST = "SELECT " + COLUMN_ARTIST_ID + " FROM " +
            TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = ?";
    public static final String QUERY_ALBUM = "SELECT " + COLUMN_ALBUM_ID + " FROM " +
            TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";
    public static final String QUERY_SONGS_BY_ARTIST = "SELECT * FROM " + TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS
            + " ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM_ID + " = " + TABLE_ALBUMS
            + "." + COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS
            + "." + COLUMN_ALBUM_ARTIST_ID + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID
            + " WHERE "+ TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " = ?";
    public static final String QUERY_ALL_SONGS = "SELECT " + TABLE_SONGS + "." + COLUMN_SONG_TITLE
            + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME
            + " FROM " + TABLE_SONGS + " RIGHT JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "."
            + COLUMN_SONG_ALBUM_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + " RIGHT JOIN "
            + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST_ID + " = "
            + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE
            + " IS NOT NULL";
    
    // insert
    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS +
            '(' + COLUMN_ARTIST_NAME + ") VALUES(?)";
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS +
            '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST_ID + ") VALUES(?, ?)";
    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS +
            '(' + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_ALBUM_ID +
            ") VALUES(?, ?, ?)";
    // update
    public static final String UPDATE_ARTIST = "UPDATE " + TABLE_ARTISTS +
            " SET " + COLUMN_ARTIST_NAME + " = ? WHERE " + COLUMN_ARTIST_ID + " = ?";
    public static final String UPDATE_ALBUM = "UPDATE " + TABLE_ALBUMS +
            " SET " + COLUMN_ALBUM_NAME + " = ? WHERE " + COLUMN_ALBUM_ID + " = ?";
    public static final String UPDATE_SONG = "UPDATE " + TABLE_SONGS +
            " SET " + COLUMN_SONG_TITLE + " = ? WHERE " + COLUMN_SONG_ID + " = ?";
    
    // delete
    public static final String DELETE_ARTIST = "DELETE FROM " +
            TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_ID + " = ?";
    public static final String DELETE_ALBUM = "DELETE FROM " +
            TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ID + " = ?";
    public static final String DELETE_SONG = "DELETE FROM " +
            TABLE_SONGS + " WHERE " + COLUMN_SONG_ID + " = ?";
    
    // username and password to server
    private static final String USERNAME = "Hai";
    private static final String PASSWORD = "1552003321";
    // singleton
    private static final Datasource instance = new Datasource();
    // connection and prepared statements
    private Connection connection = null;
    private PreparedStatement insertArtists = null;
    private PreparedStatement insertAlbums = null;
    private PreparedStatement insertSongs = null;
    private PreparedStatement queryArtist = null;
    private PreparedStatement queryAlbum = null;
    private PreparedStatement queryAlbumsByArtistID = null;
    private PreparedStatement querySongByAlbumID = null;
    private PreparedStatement querySongByArtist = null;
    private PreparedStatement queryAllSongs = null;
    private PreparedStatement updateArtist = null;
    private PreparedStatement updateAlbum = null;
    private PreparedStatement updateSong = null;
    private PreparedStatement deleteArtist = null;
    private PreparedStatement deleteAlbum = null;
    private PreparedStatement deleteSong = null;
    
    private Datasource() {
    }
    
    public static Datasource getInstance() {
        return instance;
    }
    
    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            insertArtists = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertAlbums = connection.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertSongs = connection.prepareStatement(INSERT_SONGS);
            queryArtist = connection.prepareStatement(QUERY_ARTIST);
            queryAlbum = connection.prepareStatement(QUERY_ALBUM);
            querySongByAlbumID = connection.prepareStatement(QUERY_SONG_BY_ALBUM_ID);
            querySongByArtist = connection.prepareStatement(QUERY_SONGS_BY_ARTIST);
            queryAlbumsByArtistID = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
            queryAllSongs = connection.prepareStatement(QUERY_ALL_SONGS);
            updateArtist = connection.prepareStatement(UPDATE_ARTIST);
            updateAlbum = connection.prepareStatement(UPDATE_ALBUM);
            updateSong = connection.prepareStatement(UPDATE_SONG);
            deleteArtist = connection.prepareStatement(DELETE_ARTIST);
            deleteAlbum = connection.prepareStatement(DELETE_ALBUM);
            deleteSong = connection.prepareStatement(DELETE_SONG);
            
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public void close() {
        try {
            if (insertArtists != null) insertArtists.close();
            if (insertAlbums != null) insertAlbums.close();
            if (insertSongs != null) insertSongs.close();
            if (queryArtist != null) queryArtist.close();
            if (queryAlbum != null) queryAlbum.close();
            if (queryAlbumsByArtistID != null) queryAlbumsByArtistID.close();
            if (queryAllSongs != null) queryAllSongs.close();
            if (updateArtist != null) updateArtist.close();
            if (updateAlbum != null) updateAlbum.close();
            if (updateSong != null) updateSong.close();
            if (deleteArtist != null) deleteArtist.close();
            if (querySongByAlbumID != null) querySongByAlbumID.close();
            if (querySongByArtist != null) querySongByArtist.close();
            if (deleteAlbum != null) deleteAlbum.close();
            if (deleteSong != null) deleteSong.close();
            
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY LOWER(");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(")");
            if (sortOrder == ORDER_BY_DESC) sb.append("DESC");
            else sb.append("ASC");
        }
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new com.example.musicapp.model.Artist();
                artist.setId(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
                Thread.sleep(30);
            }
            return artists;
        } catch (SQLException | InterruptedException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Album> queryAlbumsByArtistID(int id) {
        try {
            queryAlbumsByArtistID.setInt(1, id);
            ResultSet result = queryAlbumsByArtistID.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (result.next()) {
                Album album = new Album();
                album.setId(result.getInt(1));
                album.setName(result.getString(2));
                album.setArtistId(id);
                albums.add(album);
            }
            return albums;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Song> querySongsByAlbumID(int id) {
        try {
            querySongByAlbumID.setInt(1, id);
            ResultSet result = querySongByAlbumID.executeQuery();
            List<Song> songs = new ArrayList<>();
            while (result.next()) {
                Song song = new Song();
                song.setId(result.getInt(1));
                song.setTrack(result.getInt(2));
                song.setName(result.getString(3));
                song.setAlbumId(id);
                songs.add(song);
            }
            return songs;
        } catch (SQLException e) {
            System.out.println("Failed to query songs by album id: " + id);
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Song> querySongByArtist(int id) {
        try {
            querySongByArtist.setInt(1, id);
            ResultSet result = querySongByArtist.executeQuery();
            List<Song> songs = new ArrayList<>();
            while (result.next()) {
                Song song = new Song();
                song.setId(result.getInt(1));
                song.setTrack(result.getInt(2));
                song.setName(result.getString(3));
                song.setAlbumId(0);
                songs.add(song);
            }
            return songs;
        } catch (SQLException e) {
            System.out.println("Failed to query song by artist!");
            e.printStackTrace();
            return null;
        }
    }
    
    public List<SongDetail> queryAllSong() {
        try {
            ResultSet result = queryAllSongs.executeQuery();
            List<SongDetail> songs = new ArrayList<>();
            while (result.next()) {
                SongDetail song = new SongDetail();
                song.setTitle(result.getString(1));
                song.setAlbum(result.getString(2));
                song.setArtist(result.getString(3));
                songs.add(song);
            }
            return songs;
        } catch (SQLException e) {
            System.out.println("Failed to query all songs!");
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean updateArtist(int id, String newName) {
        try {
            updateArtist.setString(1, newName);
            updateArtist.setInt(2, id);
            int affectedRecords = updateArtist.executeUpdate();
            return affectedRecords == 1;
        } catch (SQLException e) {
            System.out.println("Failed to update artist name, id: " + id);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateAlbum(int id, String newName) {
        try {
            updateAlbum.setString(1, newName);
            updateAlbum.setInt(2, id);
            int affected = updateAlbum.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.out.println("Failed to update album, id: " + id);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateSong(int id, String newName) {
        try {
            updateSong.setString(1, newName);
            updateSong.setInt(2, id);
            int affected = updateAlbum.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.out.println("Failed to update song!");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteArtist(int id) {
        try {
            deleteArtist.setInt(1, id);
            int affectedRecords = deleteArtist.executeUpdate();
            return affectedRecords == 1;
        } catch (SQLException e) {
            System.out.println("Failed to delete artist name, id: " + id);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteAlbum(int id) {
        try {
            deleteAlbum.setInt(1, id);
            int affected = deleteAlbum.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.out.println("Failed to delete album!");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteSong(int id) {
        try {
            deleteSong.setInt(1, id);
            int affected = deleteSong.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.out.println("Failed to delete song!");
            e.printStackTrace();
            return false;
        }
    }
    
    public int getCount(String table) {
        String sql = "SELECT COUNT(*) AS count FROM " + table;
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            int count = results.getInt("count");
            System.out.format("Count = %d\n", count);
            return count;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    private int insertArtist(String name) throws SQLException {
        queryArtist.setString(1, name);
        ResultSet results = queryArtist.executeQuery();
        if (results.next()) return results.getInt(1);
        else {
            // Insert the artist
            insertArtists.setString(1, name);
            int affectedRows = insertArtists.executeUpdate();
            if (affectedRows != 1) throw new SQLException("Couldn't insert artist!");
            ResultSet generatedKeys = insertArtists.getGeneratedKeys();
            if (generatedKeys.next()) return generatedKeys.getInt(1);
            else throw new SQLException("Couldn't get id for artist");
        }
    }
    
    private int insertAlbum(String name, int artistId) throws SQLException {
        queryAlbum.setString(1, name);
        ResultSet results = queryAlbum.executeQuery();
        if (results.next()) return results.getInt(1);
        else {
            // Insert the album
            insertAlbums.setString(1, name);
            insertAlbums.setInt(2, artistId);
            int affectedRows = insertAlbums.executeUpdate();
            if (affectedRows != 1) throw new SQLException("Couldn't insert album!");
            ResultSet generatedKeys = insertAlbums.getGeneratedKeys();
            if (generatedKeys.next()) return generatedKeys.getInt(1);
            else throw new SQLException("Couldn't get _id for album");
        }
    }
    
    public void insertSong(String title, String artist, String album, int track) {
        try {
            connection.setAutoCommit(false);
            int artistId = insertArtist(artist);
            int albumId = insertAlbum(album, artistId);
            insertSongs.setInt(1, track);
            insertSongs.setString(2, title);
            insertSongs.setInt(3, albumId);
            int affectedRows = insertSongs.executeUpdate();
            if (affectedRows == 1) connection.commit();
            else throw new SQLException("The song insert failed");
        } catch (Exception e) {
            System.out.println("Insert song exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}