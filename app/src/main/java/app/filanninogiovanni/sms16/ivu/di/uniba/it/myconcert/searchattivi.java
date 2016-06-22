package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista.ArtistaHome;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ParserXML.XMLSetListParser;

/**
 * Created by delmi on 22/06/2016.
 */

public class searchattivi {
    private LoadSetListXMLData loadSetListXMLData = new LoadSetListXMLData();
    private  String URL_ARTIST = "http://api.setlist.fm/rest/0.1/search/setlists?artistName=";

    private String query = "";
    private XMLSetListParser setListParser;
    ArrayList<Setlist> setList;


    public void getConcerti(){
        String artist ="vasco";
        artist = artist.replaceAll("\\s+","%20");
        query = URL_ARTIST + '"' + artist + '"';
        loadSetListXMLData.execute(query);
    }
    private class LoadSetListXMLData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            setListParser = new XMLSetListParser();
            setListParser.parseXML(params[0]);
            return "fatto!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setList = setListParser.parderData;
            ArtistaHome.popolaconcerti(setList);
            loadSetListXMLData=new LoadSetListXMLData();

        }

    }
}
