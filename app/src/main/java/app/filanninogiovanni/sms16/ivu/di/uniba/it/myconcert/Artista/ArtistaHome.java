package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.DetailActivity2;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.searchattivi;

/**
 * Created by Giovanni on 15/06/2016.
 */
public class ArtistaHome extends AppCompatActivity {

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
    private DrawerLayout drawerLayout;
    private ListView listViewDrawerLayout;
    private String[] optionDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private String formatJson = "&format=json";

    private String URL_TOP_FIVE = "http://mymusiclive.altervista.org/topfivesongs.php?artista=";

    private ArrayList<String> songArray;

    private RequestQueue requestQueue;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artistahome);
        context=this;
        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_artista); //information per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //drawerView.setOnClickListener(onItemClick());
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                // creates call to onPrepareOptionsMenu()
            }
        };
        //collegamento comportamento e icona per la toolbar e drawer
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        listViewDrawerLayout.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,optionDrawer));
        urlImmagine=getIntent().getStringExtra("url");
        nomeArtistaString=getIntent().getStringExtra("nome");
        cognomeArtistaString=getIntent().getStringExtra("cognome");
        aliasArtistaString=getIntent().getStringExtra("alias");
        listaCanzoni = (ListView) findViewById(R.id.listTopFiveSongs);
        artistImage = (ImageView) findViewById(R.id.artista_immagine);
        requestQueue = Volley.newRequestQueue(this);
        toolbar=(Toolbar)findViewById(R.id.tool_bar_artista);
        toolbar.setTitle(aliasArtistaString);
        toolbar.setSubtitle(nomeArtistaString+" "+cognomeArtistaString);
        toolbar.setLogo(R.drawable.ic_drawer);
        Button button=(Button)findViewById(R.id.ButtunMichele);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
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
                    listaCanzoni.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,songArray));

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
    public static void popolaconcerti(ArrayList<Setlist> setlist)
    {
        concerti=setlist;
    }
}
