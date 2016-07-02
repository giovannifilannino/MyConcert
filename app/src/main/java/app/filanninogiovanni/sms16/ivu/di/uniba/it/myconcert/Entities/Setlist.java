package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class Setlist {

    String idSetList;
    String artistName;
    String venueName;
    String city;
    String date;
    String id;
    ArrayList<String> songs;
    Bitmap cover;
    String hashTag;

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getIdSetList() {
        return idSetList;
    }

    public void setIdSetList(String idSetList) {
        this.idSetList = idSetList;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<String> songs) {
        this.songs = songs;
    }
}
