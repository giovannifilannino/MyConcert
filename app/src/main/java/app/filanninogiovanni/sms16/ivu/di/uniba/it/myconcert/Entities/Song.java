package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities;

import java.util.ArrayList;

/**
 * Created by gianni on 29/06/16.
 */
public class Song {

    private String title;
    private String artist;
    private boolean checked = false;


    public Song(){

    }


    public Song(String title,String artist){
        this.title = title;
        this.artist = artist;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setArtist(String artist){
        this.artist=artist;
    }

    public String getArtist() {
        return artist;
    }
}
