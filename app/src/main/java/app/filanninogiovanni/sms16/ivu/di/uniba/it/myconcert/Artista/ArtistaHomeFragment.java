package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.AdapterSongsArtista;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;


/**
 * Created by Michele on 29/06/2016.
 */
public class ArtistaHomeFragment extends Fragment {

    private ImageView findImg;
    private TextView nomeArtista;
    RecyclerView.LayoutManager mLayoutManager;
    private TextView luogo;
    private TextView ext;
    private TextView benvenuto;
    private TextView tit;
    private RecyclerView listaCanzoni;
    //private ImageView artistImage;
    public static ArrayList<Setlist> concerti;
    private Toolbar toolbar;
    TextView canzoni;
    int idImg[]={R.drawable.uno,R.drawable.due,R.drawable.tre,R.drawable.quattro,R.drawable.cinque};
    ArrayList<Setlist> list = new ArrayList<Setlist>();
    private String urlImmagine;
    private String nomeArtistaString;
    private String cognomeArtistaString;
    private String aliasArtistaString;
    double[] cor=new double[2];
    private DrawerLayout drawerLayout;
    public static Bitmap immagine;
    String  luogoConcerto="";
    String extra="";
    private ListView listViewDrawerLayout;
    private ArrayList<String> optionDrawer = new ArrayList<String>();
    private ActionBarDrawerToggle mDrawerToggle;
    private String formatJson = "&format=json";

    private String URL_TOP_FIVE = "http://mymusiclive.altervista.org/topfivesongs.php?artista=";
    String urlPrimoConcerto="http://mymusiclive.altervista.org/getPrimoConcertoAttivo.php?username=";

    private ArrayList<String> songArray;
    public static String HashTag = "";
    private RequestQueue requestQueue;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artista_home_fragment, container, false);
    }


    @Override
       public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
        context=getActivity();
        listaCanzoni = (RecyclerView) getActivity().findViewById(R.id.listTopFiveSongs);
        findImg=(ImageView)getActivity().findViewById(R.id.findImg);
        //artistImage = (ImageView) getActivity().findViewById(R.id.artista_immagine);
        requestQueue = Volley.newRequestQueue(getActivity());
        luogo=(TextView) getActivity().findViewById(R.id.txtConc);
        benvenuto=(TextView) getActivity().findViewById(R.id.benvenuto);
        ext=(TextView) getActivity().findViewById(R.id.txtConc3);
        tit=(TextView) getActivity().findViewById(R.id.txtConc4);
        canzoni=(TextView) getActivity().findViewById(R.id.scritta);
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar_artista);
        toolbar.setTitle("HOME");
        benvenuto.setText(getResources().getText(R.string.benvenuto)+" "+aliasArtistaString);
        String ali=aliasArtistaString.replaceAll("\\s+", "%20");
        String urlapp=urlPrimoConcerto+'"'+ali+'"'; double longitude;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlapp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String postoConcerto="";
                String cittaConcerto="";
                JSONObject jsonObject;
                JSONArray jsonArray=response;
                try {
                    if (jsonArray.toString().compareToIgnoreCase("[]")!=0){
                        Setlist conc = new Setlist();
                        jsonObject = response.getJSONObject(0);
                        cittaConcerto = jsonObject.getString("CittaConcerto");
                        luogoConcerto = cittaConcerto;
                        extra=getString(R.string.scopri);
                        ext.setText(extra);
                        luogo.setText(luogoConcerto);
                        HashTag = jsonObject.getString("HashTag");
                        conc.setCity(cittaConcerto);

                    postoConcerto = jsonObject.getString("PostoConcerto");
                    conc.setVenueName(postoConcerto);
                    Geocoder coder = new Geocoder(context);
                    try {
                        ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(cittaConcerto + " " + postoConcerto, 50);
                        for (Address add : adresses) {
                            cor[0] = add.getLongitude();
                            cor[1] = add.getLatitude();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                    else {
                        luogoConcerto = "";
                        extra="";
                        tit.setText(getText(R.string.noConc));
                        ext.setText(extra);
                        canzoni.setText(getText(R.string.nosongs));
                        luogo.setText(luogoConcerto);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

        findImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(extra.compareToIgnoreCase("")!=0) {
                    Uri gmmIntentUri=Uri.parse("geo:<" + cor[1]  + ">,<" + cor[0] + ">?q=<" + cor[1]  + ">,<" + cor[0] + ">(" + "Ecco il tuo concerto" + ")");
                   // Uri gmmIntentUri = Uri.parse("geo:" + cor[1] + "," + cor[0]);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            }


        });
        final ImageRequest imageRequest = new ImageRequest(urlImmagine, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // artistImage.setImageBitmap(response);
                //artistImage.setScaleType(ImageView.ScaleType.FIT_XY);
                immagine=response;
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(imageRequest);

        songArray = new ArrayList<String>();


        String app = aliasArtistaString.replaceAll("\\s+", "%20");
        String url = URL_TOP_FIVE + '"' + app + '"' + formatJson;



        fillSongArray(url);




    }

    private void fillSongArray(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < 5; i++) {
                        jsonObject = response.getJSONObject(i);
                        songArray.add(jsonObject.getString("NomeCanzone"));
                    }
                    ////////////
                    AdapterSongsArtista adapterSongsArtista=new AdapterSongsArtista(songArray,idImg,context);
                    listaCanzoni.setAdapter(adapterSongsArtista);
                    mLayoutManager=new LinearLayoutManager(context);
                    listaCanzoni.setLayoutManager(mLayoutManager);

                } catch (Exception e) {
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

    public static void popolaconcerti(ArrayList<Setlist> setlist) {
        concerti = setlist;
    }
}
