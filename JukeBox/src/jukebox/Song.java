/**
 * Author: Dhruv Rajpurohit
 * Description: The code is the Song class for jukebox which implements comparable
 */

package jukebox;


public class Song implements Comparable<Song> {
    private final String artist, song;

    /**
     * The function is the constructor for the song class
     * @param artist the artist of the song
     * @param song the song
     */
    public Song(String artist, String song){
        this.artist = artist;
        this.song = song;
    }

    /**
     * The function the accessor for artist
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * The function is the accessor of song
     * @return the song
     */
    public String getSong(){
        return song;
    }

    /**
     * The function checks if there is the same song with the same artist
     * @param other data which is compared with the current object
     * @return true or false
     */
    public boolean equals(Object other){
        if (other instanceof Song){
            Song gi = (Song)other;
            return gi.artist.equals(this.artist) && gi.song.equals(this.song);
        }
        else {
            return false;
        }
    }

    /**
     * The function computes the hashcode artist and song
     * @return
     */
    @Override
    public int hashCode(){
        return song.hashCode() + artist.hashCode();
    }

    /**
     * The function compares two songs and artists
     * @param other the other song
     * @return 0, -1, 1
     */
    @Override
    public int compareTo(Song other) {
        int result = artist.compareTo(other.artist);
        if (result == 0){
            return song.compareTo(other.song);
        }
        else{
            return result;
        }
    }
}
