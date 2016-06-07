package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class Setlist {

    String idSetList;
    String artistName;
    String venueName;
    String city;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String[] songs;

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

    public String[] getSongs() {
        return songs;
    }

    public void setSongs(String[] songs) {
        this.songs = songs;
    }
}
