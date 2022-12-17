package org.example;

import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {

    @org.junit.jupiter.api.Test
    void toSQL() {
        Artist artist;
        artist = new Artist("Shakira");
        Song s = new Song("Amarillo");
        ArrayList<Song> s_list = new ArrayList<Song>();
        s_list.add(s);
        artist.setSongs(s_list);
        assertEquals("insert into artist (id, nAlbums, name, nSongs) values (1, 0, 'Shakira', 1);", artist.toSQL());
    }

    @org.junit.jupiter.api.Test
    void fromSQL() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists artist");
            statement.executeUpdate("create table artist (id integer, nAlbums integer, name string, nSongs integer)");

            Artist artist;
            artist = new Artist("Shakira");
            Song s = new Song("Amarillo");
            ArrayList<Song> s_list = new ArrayList<Song>();
            s_list.add(s);
            artist.setSongs(s_list);
            statement.executeUpdate(artist.toSQL());
            ResultSet rs = statement.executeQuery("select name from artist where id=1");
            assertEquals(rs.getString("name"), "Shakira");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}