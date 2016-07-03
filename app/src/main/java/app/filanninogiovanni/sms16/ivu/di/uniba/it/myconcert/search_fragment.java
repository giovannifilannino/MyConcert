package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deezer.sdk.model.AImageOwner;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Artist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ParserXML.XMLSetListParser;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.DeezerArtist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.ErrorClass;


/**
 * Created by Giovanni on 03/06/2016.
 */
public class search_fragment extends Fragment {

    private XMLSetListParser setListParser;
    private  String URL_ARTIST = "http://api.setlist.fm/rest/0.1/search/setlists?artistName=";
    private String URL_VENUES = "http://api.setlist.fm/rest/0.1/search/setlists?venueName=";
    private String URL_COMBINED = "&venueName=";
    private String URL_ARTIST_CONCERT = "http://mymusiclive.altervista.org/concertiAttiviArtista.php?username=";

    private EditText name_artist;
    private EditText name_venue;
    private Button search_button;
    private OnSearch onSearch;
    private LoadSetListXMLData loadSetListXMLData = new LoadSetListXMLData();
    ArrayList<Setlist> setList = new ArrayList<Setlist>();
    private  boolean FROM_VENUES = false;
    private boolean FROM_MYCONCERTDB = false;
    private ArrayList songsConcerto = new ArrayList();

    private String urlARtistCover;

    private RequestQueue requestQueue;
    private String artist;

    private DeezerArtist deezerArtist;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        onSearch = (OnSearch) context;
    }

    private String query = "";

    public interface OnSearch{
        public void searchStart(ArrayList<Setlist> urlDaCercare, String urlCover);
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
        deezerArtist = DeezerArtist.getIstance(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSetListXMLData = new LoadSetListXMLData();
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
                artist = name_artist.getText().toString();
                String artistQuery = null;
                try {
                    artistQuery  = URLEncoder.encode(artist, Charset.defaultCharset().name());
                } catch (Exception e){

                }
                artist = artist.replaceAll("\\s+","%20");
                query = URL_ARTIST + '"' + artist + '"';
                fillArrayByDB(artist);
                loadSetListXMLData.execute(query,artistQuery);


            } else if(name_venue.getText().toString().compareTo("") != 0){
                String venues = name_venue.getText().toString();
                venues = venues.replaceAll("\\s+","%20");
                query = URL_VENUES + '"' + venues + '"';
                FROM_VENUES = true;
                loadSetListXMLData.execute(query);
            }


        }
    };


    private void fillArrayByDB(String artist) {
        String url = URL_ARTIST_CONCERT + '"' + artist + '"';

        final JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                JSONObject jsonObject;
                Setlist setlist;
                for(int i=0; i<jsonArray.length(); i++){
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                        setlist = new Setlist();
                        setlist.setArtistName(jsonObject.getString("PseArtista"));
                        setlist.setCity(jsonObject.getString("CittaConcerto"));
                        setlist.setDate(jsonObject.getString("Data"));
                        setlist.setVenueName(jsonObject.getString("PostoConcerto"));
                        setlist.setId(jsonObject.getString("IdConcerto"));
                        setList.add(setlist);
                        FROM_MYCONCERTDB = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }





    private class LoadSetListXMLData extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            setListParser = new XMLSetListParser();
            setListParser.parseXML(params[0]);
            String idArtist = "";
            if(params.length>1){
            try {
                idArtist = deezerArtist.getIdArtist(params[1]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DeezerError deezerError) {
                deezerError.printStackTrace();
            }
            }
            return idArtist;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setList.addAll(setListParser.parderData);
            String json = s;
            JSONObject jsonObject = null;
            JSONObject trueJsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                trueJsonObject = jsonArray.getJSONObject(0);
                urlARtistCover = trueJsonObject.getString("picture_big");
            } catch (JSONException e) {
                e.printStackTrace();

            }
            if(!(urlARtistCover==null)) {
                onSearch.searchStart(setList, urlARtistCover);
                loadSetListXMLData = new LoadSetListXMLData();
            } else if(FROM_VENUES){
                onSearch.searchStart(setList, "");
                loadSetListXMLData = new LoadSetListXMLData();
            } else if(FROM_MYCONCERTDB){
                onSearch.searchStart(setList, "");
                loadSetListXMLData = new LoadSetListXMLData();
            }
        }

    }

}