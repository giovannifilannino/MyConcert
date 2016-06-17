package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ParserXML.XMLSetListParser;


/**
 * Created by Giovanni on 03/06/2016.
 */
public class search_fragment extends Fragment {

    private XMLSetListParser setListParser;
    private  String URL_ARTIST = "http://api.setlist.fm/rest/0.1/search/setlists?artistName=";
    private String URL_VENUES = "http://api.setlist.fm/rest/0.1/search/setlists?venueName=";
    private String URL_COMBINED = "&venueName=";
    private EditText name_artist;
    private EditText name_venue;
    private Button search_button;
    private OnSearch onSearch;
    private LoadSetListXMLData loadSetListXMLData = new LoadSetListXMLData();



    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        onSearch = (OnSearch) context;
    }

    private String query = "";

    public interface OnSearch{
        public void searchStart(ArrayList<Setlist> urlDaCercare);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.find_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name_artist =(EditText) getActivity().findViewById(R.id.artist_name_search);
        name_venue = (EditText) getActivity().findViewById(R.id.venue_name_search);
        search_button = (Button) getActivity().findViewById(R.id.search_button);
        search_button.setOnClickListener(artistSearchButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    View.OnClickListener artistSearchButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(name_artist.getText().toString().compareTo("") != 0 && name_venue.getText().toString().compareTo("") != 0){
                String artist = name_artist.getText().toString();
                String venues = name_venue.getText().toString();
                artist = artist.replaceAll("\\s+","%20");
                venues = venues.replaceAll("\\s+","%20");
                query = URL_ARTIST + '"' + artist + '"' + URL_COMBINED + '"' + venues + '"';
                loadSetListXMLData.execute(query);
            }
            else if(name_artist.getText().toString().compareTo("") != 0){
                String artist = name_artist.getText().toString();
                artist = artist.replaceAll("\\s+","%20");
                query = URL_ARTIST + '"' + artist + '"';
                loadSetListXMLData.execute(query);
            } else if(name_venue.getText().toString().compareTo("") != 0){
                String venues = name_venue.getText().toString();
                venues = venues.replaceAll("\\s+","%20");
                query = URL_VENUES + '"' + venues + '"';
                loadSetListXMLData.execute(query);
            }


        }
    };




    private class LoadSetListXMLData extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            setListParser = new XMLSetListParser();
            setListParser.parseXML(params[0]);
            return "fatto!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Setlist> setList = setListParser.parderData;
            onSearch.searchStart(setList);
            loadSetListXMLData=new LoadSetListXMLData();
        }

    }


}
