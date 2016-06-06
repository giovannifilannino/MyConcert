package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Giovanni on 03/06/2016.
 */
public class search_fragment extends Fragment {

    private XMLArtistParser artistParser;
    private XMLVenueParser venuesParser;

    private  String URL_ARTIST = "http://api.setlist.fm/rest/0.1/search/artists?artistName=";
    private String URL_VENUES = "http://api.setlist.fm/rest/0.1/search/venues?name=";
    private EditText name_artist;
    private EditText name_venue;
    private Button search_button;
    private LoadXMLData loadXML = new LoadXMLData();
    private LoadVenuesXMLData loadVenuesXMLData = new LoadVenuesXMLData();

    private String query = "";

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
            if(name_artist.getText().toString().compareTo("") != 0){
                String artist = name_artist.getText().toString();
                artist = artist.replaceAll("\\s+","%20");
                query = URL_ARTIST + '"' + artist + '"';
                loadXML.execute(query);
            } else if(name_venue.getText().toString().compareTo("") != 0){
                String venues = name_venue.getText().toString();
                venues = venues.replaceAll("\\s+","%20");
                query = URL_VENUES + '"' + venues + '"';
                loadVenuesXMLData.execute(query);
            }


        }
    };



    private class LoadXMLData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            artistParser = new XMLArtistParser();
            artistParser.parseXML(params[0]);
            return artistParser.parsedData.get(0).getName();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        }
    }

    private class LoadVenuesXMLData extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            venuesParser = new XMLVenueParser();
            venuesParser.parseXML(params[0]);
            return venuesParser.parderData.get(0).getName();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        }
    }


}
