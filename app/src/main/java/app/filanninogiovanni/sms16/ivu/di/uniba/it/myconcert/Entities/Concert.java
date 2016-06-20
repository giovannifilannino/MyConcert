package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities;

/**
 * Created by hp01 on 20/06/2016.
 */
public class Concert {
    String Artista;
    String Data;
    String Luogo;
    String Citta;

    public void setArtista(String artista){ Artista = artista;}
    public String getArtista(){return Artista;}
    public void setData(String data){ Data = data;}
    public String getData(){return Data;}
    public void setLuogo(String luogo){ Luogo = luogo;}
    public String getLuogo(){return Luogo;}
    public void setCitta(String citta){ Citta = citta;}
    public String getCitta(){return Citta;}
}
