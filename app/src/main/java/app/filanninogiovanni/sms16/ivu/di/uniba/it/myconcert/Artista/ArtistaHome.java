package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;


import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.DeezerArtist;


/**
 * Created by Giovanni on 15/06/2016.
 */
public class ArtistaHome extends AppCompatActivity {

    private String urlImmagine;
    private String nomeArtistaString;
    private String cognomeArtistaString;
    private String aliasArtistaString;
    private ListView listViewDrawerLayout;
    private DrawerLayout drawerLayout;
    public String data;
    public String postoConcerto;
    public String cittaConcerto;
    public String idConcerto;
    RequestQueue requestQueue;
    public String pseArtista;
    private ArrayList<String> optionDrawer = new ArrayList<String>();
    FragmentManager fragmentManager;
    String urlPHPpart = "http://mymusiclive.altervista.org/concertiAttiviArtista.php?username=";

    Context context;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artistahome);
        urlImmagine=getIntent().getStringExtra("url");
        nomeArtistaString=getIntent().getStringExtra("nome");
        cognomeArtistaString=getIntent().getStringExtra("cognome");
        aliasArtistaString=getIntent().getStringExtra("alias");
        context = this;
        requestQueue = Volley.newRequestQueue(this);
        optionDrawer.add("CONCERTI ATTIVI");
        optionDrawer.add(" ATTIVI");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //information per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, optionDrawer);
        listViewDrawerLayout.setAdapter(adapter);
        listViewDrawerLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //chiama resultFragmentArtisti
            }
        });
        fragmentManager =getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ArtistaHomeFragment artistaHome=new ArtistaHomeFragment();
        artistaHome.setNomeArtistaString(nomeArtistaString);
        artistaHome.setCognomeArtitaString(cognomeArtistaString);
        artistaHome.setAliasArtistaString(aliasArtistaString);
        artistaHome.setUrlImmagine(urlImmagine);
        fragmentTransaction.replace(R.id.content_frame, artistaHome);
        fragmentTransaction.commit();
        String artista=aliasArtistaString.replaceAll("\\s+","%20");
        String url=urlPHPpart+'"'+artista + '"';
        JsonArrayRequest arrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                Log.d("mlml",jsonArray.toString());
                JSONObject jsonObject;
                    try{
                        jsonObject = response.getJSONObject(0);
                        data = jsonObject.getString("Data");
                        postoConcerto = jsonObject.getString("PostoConcerto");
                        cittaConcerto = jsonObject.getString("CittaConcerto");
                        pseArtista=jsonObject.getString("PseArtista");
                        idConcerto=jsonObject.getString("IdConcerto");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }
    
}
