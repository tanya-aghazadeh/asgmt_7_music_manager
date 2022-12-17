package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Song Class
 *
 * @author Tanya Aghazadeh
 */
public class Song extends Entity {
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;

    public Song(String name) {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";

    }
    public Song(String name, int length) {
        super(name);
        duration = new SongInterval(length);
        genre = "";
    }

    /**
     * This function gets the genre for a song.
     *
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * This function sets the Genre for a song.
     *
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * This function sets the Length for a song.
     *
     * @param length
     */
    public void setLength(int length) {
        duration = new SongInterval(length);
   }

    /**
     * This function shows the length of a song.
     */
   public String showLength() {
        return duration.toString();
   }

    /**
     * This function gets the album for a song.
     *
     * @return album
     */
    protected Album getAlbum() {
        return album;
    }

    /**
     * This function sets the album for a song.
     *
     * @param album
     *
     */
    protected void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * This function gets the performer for a song.
     *
     * @return performer
     */
    public Artist getPerformer() {
        return performer;
    }

    /**
     * This function sets the performer for a song.
     *
     * @param performer
     */
    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    public String toString() {
        return super.toString() + " " + this.performer + " " + this.album + " " + this.duration;

    }

    /**
     * This function makes a SQL command.
     *
     * @return string
     */
    public String toSQL() {
        return "insert into songs (id, name, album, artist) values (" +
                this.entityID + ", '" + this.name + "', " +
                album.entityID + ", " + performer.entityID  + ");";
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
        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }

    }



}
