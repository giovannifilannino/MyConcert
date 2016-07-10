package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.AdapterItemDrawer;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista.ArtistaHome;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.NoSongsFound;
import io.fabric.sdk.android.Fabric;

public class cerca extends AppCompatActivity implements search_fragment.OnSearch {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private ListView listViewDrawerLayout;
    private String[] optionDrawer;
    private NoSongsFound noSongsFound;
    private ResultFragment resultFragment = new ResultFragment();
    private int choise;
    private String nomeArtista;
    private String cognomeArtista;
    private String aliasArtista;
    private String urlImmagine;
    private RequestQueue requestQueue;
    private String usernameS;
    Context context;
    private ProgressDialog dialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private String searchString;
    private String setlistString;
    private String concertString;

    private Fragment chosenConcerts;
    private Fragment playlist_fragment;

    private static final String TWITTER_KEY = "9R1qMlXL3qRX4wwkKasPn6yvE";
    private static final String TWITTER_SECRET = "kTZ7Z9aU0b04igbUAp12AjgR0tcXXnHvPVc90E0t6aRUx5bh24";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.irko);
        context = this;
        searchString = getResources().getString(R.string.search);
        setlistString = getResources().getString(R.string.setlistmy);
        concertString = getResources().getString(R.string.concert);
        chosenConcerts = new ChosenConcerts();
        playlist_fragment=new Playlist_fragment();
        recyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //information per la comparsa del menu laterale
        choise = getIntent().getIntExtra("artista", 0);
        nomeArtista = getIntent().getStringExtra("nomeArtista");
        cognomeArtista = getIntent().getStringExtra("cognomeArtista");
        aliasArtista = getIntent().getStringExtra("aliasArtista");
        urlImmagine = getIntent().getStringExtra("urlImmagine");
        String nomeUtente=getIntent().getStringExtra("nome");
        String cognomeUtente=getIntent().getStringExtra("cognome");
        toolbar = (Toolbar) findViewById(R.id.tool_bar_find);
        setSupportActionBar(toolbar);
        layoutManager = new LinearLayoutManager(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());

        int ICONS[] = {R.drawable.ic_home_black_24dp, R.drawable.ic_library_music_black_24dp, R.drawable.ic_tv_black_24dp};
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.account);
        AdapterItemDrawer adapterItemDrawer = new AdapterItemDrawer(optionDrawer, ICONS,nomeUtente+" "+cognomeUtente , loginFragment.actualUsername, bitmap, this);

        recyclerView.setAdapter(adapterItemDrawer);

        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment search = new search_fragment();
        fragmentTransaction.replace(R.id.content_frame, search).commit();

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();

            }
        };



        //collegamento comportamento e icona per la toolbar e drawer
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();


        adapterItemDrawer.setOnItemClickListener(new AdapterItemDrawer.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, String scelta) {
                if (optionDrawer[position].compareToIgnoreCase(searchString) == 0) {
                    drawerLayout.closeDrawers();
                    fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment search = new search_fragment();
                    fragmentTransaction.replace(R.id.content_frame, search).commit();
                } else if (optionDrawer[position].compareToIgnoreCase(setlistString) == 0) {
                    drawerLayout.closeDrawers();
                    fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, playlist_fragment).commit();
                } else if (optionDrawer[position].compareToIgnoreCase(concertString) == 0) {
                    drawerLayout.closeDrawers();
                    fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, chosenConcerts).commit();
                }
            }
        });



    }


        @Override
        public void searchStart ( final ArrayList<Setlist> urlDaCercare, String urlCover){
            fragmentManager = getFragmentManager();

            dialog = new ProgressDialog(this);

            dialog.setMessage(getResources().getString(R.string.loading_photo));
            dialog.show();
            if (urlCover.compareTo("") == 0) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                dialog.hide();
                fragmentTransaction.replace(R.id.content_frame, resultFragment).addToBackStack("miro").commit();
                resultFragment.riempiArray(urlDaCercare);
            } else {
                ImageRequest imageRequest = new ImageRequest(urlCover, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        for (Setlist set : urlDaCercare) {
                            set.setCover(response);
                        }
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        dialog.hide();
                        fragmentTransaction.replace(R.id.content_frame, resultFragment).addToBackStack("miro").commit();
                        resultFragment.riempiArray(urlDaCercare);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();

                    }
                });

                requestQueue.add(imageRequest);

            }
        }
}
