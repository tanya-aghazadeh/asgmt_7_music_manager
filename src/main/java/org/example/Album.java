package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Album Class
 *
 * @author Tanya Aghazadeh
 */
public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    public Album(String name) {
        super(name);
        songs = new ArrayList<>();
        artist = new Artist("");
    }
    /**
     * This function gets the Name for an album.
     *
     * @return name
     */
    public String getName() {
        System.out.println("this is an album" + super.getName());
        return name;
    }
    /**
     * This function checks if two albums are equal.
     *
     * @param otherAlbum
     *
     * @return true/false
     */
    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * This function gets the Songs list for an album.
     *
     * @return songs
     */
    protected ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * This function sets the songs list for an album.
     *
     * @param songs
     */
    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * This function gets the Artist for an album.
     *
     * @return artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * This function sets Artist for an album.
     *
     * @param  artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * This function makes a SQL command.
     *
     * @return the command to SQL
     */
    public String toSQL() {
        return "insert into albums (id, name, nSongs, artist) values (" +
                this.entityID + ", '" + this.name + "', " +
                this.songs.size() + ", " +
                this.artist.entityID  + ");";
    }

    /**
     * This function gets info from SQL.
     *
     * @param rs
     */
    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }

    }

}
