package org.example;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @org.junit.jupiter.api.Test
    void toSQL() {
        Album album;
        album = new Album("El Dorado");
        Song s = new Song("Amarillo");
        ArrayList<Song> s_list = new ArrayList<Song>();
        s_list.add(s);
        album.setSongs(s_list);
        assertEquals("insert into Album (id, name, nSongs, artist) values (1, 'El Dorado', 1, 2);", album.toSQL());
    }

    @org.junit.jupiter.api.Test
    void fromSQL() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists album");
            statement.executeUpdate("create table album (id integer, name string, nSongs integer, artist integer)");

            Album album;
            album = new Album("El Dorado");
            Song s = new Song("Amarillo");
            ArrayList<Song> s_list = new ArrayList<Song>();
            s_list.add(s);
            album.setSongs(s_list);
            statement.executeUpdate(album.toSQL());
            ResultSet rs = statement.executeQuery("select name from album where id=1");
            assertEquals(rs.getString("name"), "El Dorado");
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