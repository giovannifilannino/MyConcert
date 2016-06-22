package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.searchattivi;

/**
 * Created by Giovanni on 15/06/2016.
 */
public class ArtistaHome extends Fragment {

    private TextView nomeArtista;
    private TextView cognomeArtista;
    private TextView aliasArtista;
    private ListView listaCanzoni;
    private ImageView artistImage;
    public searchattivi attivi=new searchattivi();
    public static ArrayList<Setlist> concerti;
    private Toolbar toolbar;
    private String urlImmagine;
    private String nomeArtistaString;
    private String cognomeArtistaString;
    private String aliasArtistaString;
    private String formatJson = "&format=json";

    private String URL_TOP_FIVE = "http://mymusiclive.altervista.org/topfivesongs.php?artista=";

    private ArrayList<String> songArray;

    private RequestQueue requestQueue;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listaCanzoni = (ListView) getActivity().findViewById(R.id.listTopFiveSongs);
        artistImage = (ImageView) getActivity().findViewById(R.id.artista_immagine);
        requestQueue = Volley.newRequestQueue(getActivity());
        toolbar=(Toolbar)getActivity().findViewById(R.id.tool_bar_artista);
        toolbar.setTitle(aliasArtistaString);
        toolbar.setSubtitle(nomeArtistaString+" "+cognomeArtistaString);

        ImageRequest imageRequest = new ImageRequest(urlImmagine, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                    artistImage.setImageBitmap(response);
                    artistImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }, 0,0,Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Wanna",urlImmagine);
            }
        });

        requestQueue.add(imageRequest);

        songArray = new ArrayList<String>();

        aliasArtistaString = aliasArtistaString.replaceAll("\\s+","%20");
        String url = URL_TOP_FIVE + '"' + aliasArtistaString + '"' +formatJson;


        fillSongArray(url);
        attivi.getConcerti();



    }

    private void fillSongArray(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    for(int i=0; i<5; i++){
                        jsonObject = response.getJSONObject(i);
                        songArray.add(jsonObject.getString("NomeCanzone"));
                    }
                    listaCanzoni.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,songArray));

                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artistahome, container, false);
    }

    public void setAliasArtistaString(String aliasArtistaString) {
        this.aliasArtistaString = aliasArtistaString;
    }

    public void setNomeArtistaString(String nomeArtistaString) {
        this.nomeArtistaString = nomeArtistaString;
    }

    public void setCognomeArtitaString(String cognomeArtistaString) {
        this.cognomeArtistaString = cognomeArtistaString;
    }

    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }
    public static void popolaconcerti(ArrayList<Setlist> setlist){
        concerti=setlist;
    }
}
