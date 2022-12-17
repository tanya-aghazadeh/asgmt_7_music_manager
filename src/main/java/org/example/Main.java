package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Tanya Aghazadeh
 */
public class Main {

    /**
     * This is a program for running music manager.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int nsongs = 0;
        int nalbums = 0;
        int nartists = 0;
        int option;
        String name;
        ResultSet rs;
        Connection cn = null;
        try {
            cn = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = cn.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//            statement.executeUpdate("drop table if exists artists");
//            statement.executeUpdate("create table artists (id integer, nAlbums integer, name string, nSongs integer)");
//            statement.executeUpdate("drop table if exists albums");
//            statement.executeUpdate("create table albums (id integer, name string, nSongs integer, artist integer)");
//            statement.executeUpdate("drop table if exists songs");
//            statement.executeUpdate("create table songs (id integer, name string, album integer, artist integer)");
            rs = statement.executeQuery("select * from artists");
            while (rs.next()) {
                nartists += 1;
            }
            rs = statement.executeQuery("select * from albums");
            while (rs.next()) {
                nalbums += 1;
            }
            rs = statement.executeQuery("select * from songs");
            while (rs.next()) {
                nsongs += 1;
            }
            while(true) {
                System.out.println("Enter your choice:\n" +
                        "\t1. Create an artist\n" +
                        "\t2. Create an album \n" +
                        "\t3. Create a song \n" +
                        "\t4. Display all artists \n" +
                        "\t5. Display all albums \n" +
                        "\t6. Display all songs \n" +
                        "\t7. Export playlist \n" +
                        "\t8. Write playlist to playlist.xml \n" +
                        "\t9. Exit \n");
                option = Integer.parseInt(in.next());

                if (option==1) {
                    System.out.println("Enter artist name: ");
                    name = in.next();
                    name += in.nextLine();
                    statement.executeUpdate(makeArtist(name).toSQL());
                } else if (option==2) {
                    System.out.println("Enter artist name: ");
                    name = in.next();
                    name += in.nextLine();
                    ArrayList<Album> albums = getAlbums(name);
                    System.out.println("Which album you want to add: ");
                    int num = Integer.parseInt(in.next());
                    statement.executeUpdate(albums.get(num).toSQL());
                } else if (option==3) {
                    System.out.println("Enter artist name: ");
                    String artistName = in.next();
                    artistName += in.nextLine();
                    System.out.println("Enter song name: ");
                    String songName = in.next();
                    songName += in.nextLine();
                    statement.executeUpdate(makeSong(artistName, songName).toSQL());
                } else if (option==4) {
                    rs = statement.executeQuery("select * from artists");
                    System.out.println("Artists:");
                    while (rs.next()) {
                        System.out.println("\t" + rs.getInt("id") + "." + rs.getString("name"));
                    }
                } else if (option==5) {
                    rs = statement.executeQuery("select * from albums");
                    System.out.println("Albums:");
                    while (rs.next()) {
                        System.out.println("\t" + rs.getInt("id") + "." + rs.getString("name"));
                    }
                } else if (option==6) {
                    rs = statement.executeQuery("select * from songs");
                    System.out.println("Songs:");
                    while (rs.next()) {
                        System.out.println("\t" + rs.getInt("id") + "." + rs.getString("name"));
                    }
                } else if (option==7) {
                    rs = statement.executeQuery("select * from songs");
                    System.out.println("Playlist:");
                    while (rs.next()) {
                        System.out.println("\t" + rs.getInt("id") + "." + rs.getString("name"));
                    }
                } else if (option==8) {
                    rs = statement.executeQuery("select * from songs");
                    ArrayList<Song> songs_list = new ArrayList<Song>();
                    while (rs.next()) {
                        String songName = rs.getString("name");
                        int artistId = rs.getInt("artist");
                        int albumId = rs.getInt("album");
                        Song song = new Song(songName);
                        song.entityID = rs.getInt("id");
                        song.performer.entityID = artistId;
                        song.album.entityID = albumId;
                        songs_list.add(song);
                    }
                    String output = "<?xml version=\"1.0\"  ?>\n<library>\n\t<songs>\n\t\t";
                    output += songs_list.get(0).toXML();
                    songs_list.remove(0);
                    for (Song s : songs_list) {
                        output += "\n\t\t";
                        output += s.toXML();
                    }
                    output += "\n\t</songs>\n</library>";
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("playlist.xml"));
                        writer.write(output);
                        writer.close();
                    } catch (Exception  e) {
                        System.out.println("Cannot make *.xml file: " + e);
                    }
                } else if (option==9) {
                    System.out.println("BYE!");
                    return;
                }

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (cn != null)
                    cn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * This function gets the artist ID.
     *
     * @param artistName
     *
     * @return artistId
     */
    public static Integer getArtistID(String artistName) {
        String url = "https://www.theaudiodb.com/api/v1/json/523532/search.php?s=";
        url += artistName;
        String info = getJsonString(url);
        JSONObject obj = jsonStrtoObj(info);
        JSONArray jArray = (JSONArray) obj.get("artists");
        JSONObject new_obj = (JSONObject) jArray.get(0);
        int artistId = Integer.parseInt(new_obj.get("idArtist").toString());
        return artistId;
    }

    /**
     * This function makes an Artist object by name.
     *
     * @param artistName
     */
    public static Artist makeArtist(String artistName) {
        Artist artist = new Artist(artistName);
        artist.entityID = getArtistID(artistName);
        return artist;
    }


    /**
     * This function makes a Song object by the name of artist and the name of song.
     *
     * @param artistName
     * @param songName
     */
    public static Song makeSong(String artistName, String songName) {
        Artist artist = makeArtist(artistName);

        String url = "https://theaudiodb.com/api/v1/json/523532/searchtrack.php?s=coldplay&t=yellow";
        url += artistName + "&t=" + songName;
        JSONObject obj = jsonStrtoObj(getJsonString(url));
        JSONArray jArray = (JSONArray) obj.get("track");
        JSONObject new_obj = (JSONObject) jArray.get(0);

        Song song = new Song(songName);
        song.entityID = Integer.parseInt(new_obj.get("idTrack").toString());

        Album album = new Album(new_obj.get("strAlbum").toString());
        album.entityID = Integer.parseInt(new_obj.get("idAlbum").toString());
        album.setArtist(artist);

        song.setAlbum(album);
        song.setPerformer(artist);

        return song;
    }

    /**
     * This function gets all albums of an artist.
     *
     * @param artistName
     */
    public static ArrayList<Album> getAlbums(String artistName) {
        String url = "https://www.theaudiodb.com/api/v1/json/523532/searchalbum.php?s=";
        url += artistName;
        String info = getJsonString(url);
        JSONObject obj = jsonStrtoObj(info);
        JSONArray jArray = (JSONArray) obj.get("album");
        System.out.println("List of Albums:");
        Artist ar = makeArtist(artistName);
        ArrayList<Album> album_list = new ArrayList<Album>();
        Album album;
        int i = 1;
        for (Object o : jArray) {
            JSONObject jo = (JSONObject) o;
            String albumName = jo.get("strAlbum").toString();
            album = new Album(albumName);
            System.out.println(Integer.toString(i) + "- " + albumName);
            album.entityID = Integer.parseInt(jo.get("idAlbum").toString());
            album.setArtist(ar);
            album_list.add(album);
            i += 1;
        }
        return album_list;
    }


    /**
     * This function get a json string from AudioDB.
     *
     * @param  url
     *
     * @return String
     */
    public static String getJsonString(String url) {
        URL u;
        try {
            u = new URL(url);
            URLConnection myURLConnection = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            String inputLine;
            String output = "";
            while ((inputLine = in.readLine()) != null)
                output += inputLine;
            in.close();
            return output;
        } catch (MalformedURLException e) {
            System.out.println("Page does not exist.");
            return "FAILED";
        } catch (IOException e) {
            System.out.println("Page cannot be loaded.");
            return "FAILED";
        }
    }


    /**
     * This function converts a json string to jason object.
     *
     * @param  jsonString
     *
     * @return JSONObject
     */
    public static JSONObject jsonStrtoObj(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonString);
            JSONObject jObj = (JSONObject) obj;
            return jObj;
        } catch (Exception e) {
            System.out.println("Error in parsing!" + e.toString());
        }
        return new JSONObject();
    }

}