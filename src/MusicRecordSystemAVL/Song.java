package MusicRecordSystemAVL;

/**
 *
 * @author tokta
 */
public class Song {

    public String songName;
    public String artist;
    public int ID;
    public String genre;
    public int year;

    public Song(String songName, String artist, int ID, String genre, int year) {
        this.songName = songName;
        this.artist = artist;
        this.ID = ID;
        this.genre = genre;
        this.year = year;
    }

    @Override
    public String toString() {
        return "\n{songName=" + songName + ", artist=" + artist + ", ID=" + ID + ", genre=" + genre + ", year=" + year + "}";
    }

}
