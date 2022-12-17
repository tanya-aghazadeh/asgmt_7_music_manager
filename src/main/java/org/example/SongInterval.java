package org.example;

/**
 * SongInterval Class
 *
 * @author Tanya Aghazadeh
 */
public class SongInterval {
    private int length;

    public SongInterval(int l) {
        this.length = l;
    }

    /**
     * This function converts song interval to string.
     *
     * @return string
     */
    public String toString() {
        int minutes = length / 60;
        int seconds = length % 60;
        return String.format("%d:%d", minutes, seconds);

    }

}
