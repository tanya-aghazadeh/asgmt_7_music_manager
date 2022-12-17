package org.example;

import java.util.ArrayList;

/**
 * library Class
 *
 * @author Tanya Aghazadeh
 */
public class Library {
    private ArrayList<Song> songs;

    public Library() {
        songs = new ArrayList<Song>();
    }
    /**
     * This function gets find Song for a Library.
     *
     * @param  s
     *
     * @return true/false
     */
    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    /**
     * This function gets Songs for a Library.
     *
     * @return songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * This function add Song for a Library.
     *
     * @param  s
     */
    public void addSong(Song s) {
        songs.add(s);
    }


}
