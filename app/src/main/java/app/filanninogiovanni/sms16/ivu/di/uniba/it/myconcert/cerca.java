package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class cerca extends AppCompatActivity implements search_fragment.OnSearch, ResultFragment.OnSetListSelecter , ListView.OnItemClickListener{
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.irko);
    context=this;
        recyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //information per la comparsa del menu laterale
        choise = getIntent().getIntExtra("artista",0);
        nomeArtista = getIntent().getStringExtra("nomeArtista");
        cognomeArtista = getIntent().getStringExtra("cognomeArtista");
        aliasArtista = getIntent().getStringExtra("aliasArtista");
        urlImmagine = getIntent().getStringExtra("urlImmagine");

        layoutManager = new LinearLayoutManager(this);

        int ICONS[] = {R.drawable.ic_home_black_24dp,R.drawable.ic_library_music_black_24dp,R.drawable.ic_tv_black_24dp};

        AdapterItemDrawer adapterItemDrawer =new AdapterItemDrawer(optionDrawer,ICONS,"Utente",loginFragment.actualUsername,R.drawable.account,this);

        recyclerView.setAdapter(adapterItemDrawer);

        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);

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



        fragmentManager = getFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

          Fragment search = new search_fragment();
          if(choise==0){
              fragmentTransaction.replace(R.id.content_frame, search);
          } else {
              Intent artistaHome =new Intent(context,ArtistaHome.class);
              artistaHome.putExtra("nome",nomeArtista);
              artistaHome.putExtra("cognome",cognomeArtista);
              artistaHome.putExtra("alias",aliasArtista);
              artistaHome.putExtra("url",urlImmagine);
              startActivity(artistaHome);
          }

          fragmentTransaction.commit();

    }




    @Override
    public void searchStart(final ArrayList<Setlist> urlDaCercare, String urlCover) {
        fragmentManager = getFragmentManager();

        dialog = new ProgressDialog(this);

        dialog.setMessage("Caricamento Foto...");
        dialog.show();
        if(urlCover.compareTo("")==0){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            dialog.hide();
            fragmentTransaction.replace(R.id.content_frame,resultFragment).addToBackStack("miro").commit();
            resultFragment.riempiArray(urlDaCercare);
        }else {
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

    @Override
    public  void showSongs(ArrayList<String> songs, boolean songsavaible) {

        noSongsFound = new NoSongsFound();

        fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(songsavaible) {


        } else {
            fragmentTransaction.replace(R.id.content_frame, noSongsFound).addToBackStack("").commit();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==4){
                final String[] jarray = new String[1];
                final String[] Artista = new String[1];
                final String[] dataconcerto = new String[1];
                final String[] utente = new String[1];
                ArrayList<String> concerti = null;
                String urlPHPpart = "http://mymusiclive.altervista.org/chosenConcerts.php?user=";
                String urlJSON = "http://mymusiclive.altervista.org/chosenConcerts.json";
                String usernameS = loginFragment.actualUsername;
                String urlPHP = urlPHPpart.concat(usernameS);

                HttpClient client = new DefaultHttpClient();

                try {
                    client.execute(new HttpGet(urlPHP));
                } catch(IOException e) {
                    //do something here
                }


                final ArrayList<String> finalConcerti = concerti;
                JsonArrayRequest stringRequest = new JsonArrayRequest(urlJSON,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                JSONArray jsonArray = response;
                                jarray[0] = jsonArray.toString();

                                if (checkVuoto(jarray[0])) {
                                    int i = 0;
                                    while (i < jsonArray.length()) {
                                        try {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                            Artista[0] = jsonObject.getString("Artista");
                                            dataconcerto[0] = jsonObject.getString("Data");
                                            utente[0] =jsonObject.getString("idUtenteP");
                                            addconcert(finalConcerti, Artista[0], dataconcerto[0], i);
                                            i++;


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                Intent intent = new Intent(this,ChosenConcerts.class);
                intent.putStringArrayListExtra("concertiscelti",finalConcerti);}




    }
    private boolean checkVuoto(String query){
        if(query.compareTo("[]")==0){
            return false;
        }
        return true;
    }

    private JSONObject getJson(JSONArray jsonArray){
        JSONObject result = null;
        try{
            result = jsonArray.getJSONObject(0);
        } catch (Exception e){

        }
        return result;
    }
    private void addconcert(ArrayList<String> array,String artista,String data,int i){
        array.add(i,artista.concat("-").concat(data));
    }


}
