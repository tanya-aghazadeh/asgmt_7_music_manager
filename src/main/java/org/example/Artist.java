package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Artist Class
 *
 * @author Tanya Aghazadeh
 */
public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    public Artist(String name) {
        super(name);
        songs = new ArrayList<>();
        albums = new ArrayList<>();
    }
    /**
     * This function gets the Songs list from an Artist.
     *
     * @retun songs
     */
    protected ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * This function sets the Songs list for an Artist.
     *
     * @param songs
     */
    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * This function gets the Albums for an Artist.
     *
     * @return albums
     */
    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * This function sets the Albums for an Artist.
     *
     * @param albums
     */
    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }
    /**
     * This function adds a song for an Artist.
     *
     * @param s
     */
    public void addSong(Song s) {
        songs.add(s);
    }

    /**
     * This function makes a SQL command.
     *
     * @return the command to SQL
     */
    public String toSQL() {
        return "insert into artists (id, nAlbums, name, nSongs) values (" +
                this.entityID + ", " + this.albums.size() +
                ", '" + this.name + "', " +
                this.songs.size()  + ");";
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
