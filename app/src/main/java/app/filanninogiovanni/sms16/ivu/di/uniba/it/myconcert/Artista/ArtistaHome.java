package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.support.annotation.BoolRes;
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
import java.util.Objects;


import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ResultFragment;
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
    ProgressDialog dialog;
    public String pseArtista;
    ArrayList<Setlist> concerti=new ArrayList<Setlist>();
    private ArrayList<String> optionDrawer = new ArrayList<String>();
    FragmentManager fragmentManager;
    String urlPHPpart = "http://mymusiclive.altervista.org/concertiAttiviArtista.php?username=";

    Context context;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artistahome);
        fragmentManager =getFragmentManager();
        urlImmagine=getIntent().getStringExtra("url");
        nomeArtistaString=getIntent().getStringExtra("nome");
        cognomeArtistaString=getIntent().getStringExtra("cognome");
        aliasArtistaString=getIntent().getStringExtra("alias");
        context = this;
        requestQueue = Volley.newRequestQueue(this);
        optionDrawer.add("HOME") ;
        optionDrawer.add("CONCERTI ATTIVI");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //information per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, optionDrawer);
        listViewDrawerLayout.setAdapter(adapter);
        listViewDrawerLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (optionDrawer.get(position)){
                    case "CONCERTI ATTIVI":
                        Fragment f=fragmentManager.findFragmentById(R.id.content_frame);
                        if(f!=null && f instanceof ArtistaHomeFragment){
                            dialog = new ProgressDialog(context);
                            dialog.setMessage("Caricamento..");
                            dialog.show();
                            ResultFragmentArtisti resultFragmentArtisti=new ResultFragmentArtisti();
                            goToConcert(resultFragmentArtisti);
                            concerti.clear();
                        }
                        break;
                    case "HOME":
                        Fragment c=fragmentManager.findFragmentById(R.id.content_frame);
                        if(c!=null && c instanceof ResultFragmentArtisti){
                            ArtistaHomeFragment artistaHome=new ArtistaHomeFragment();
                            artistaHome.setNomeArtistaString(nomeArtistaString);
                            artistaHome.setCognomeArtitaString(cognomeArtistaString);
                            artistaHome.setAliasArtistaString(aliasArtistaString);
                            artistaHome.setUrlImmagine(urlImmagine);
                            startTransiction(artistaHome);
                        }
                        break;
                }

            }
        });

        ArtistaHomeFragment artistaHome=new ArtistaHomeFragment();
        artistaHome.setNomeArtistaString(nomeArtistaString);
        artistaHome.setCognomeArtitaString(cognomeArtistaString);
        artistaHome.setAliasArtistaString(aliasArtistaString);
        artistaHome.setUrlImmagine(urlImmagine);
        startTransiction(artistaHome);





    }
    public void goToConcert(final ResultFragmentArtisti fragment){
        String artista=aliasArtistaString.replaceAll("\\s+","%20");
        String url=urlPHPpart+'"'+artista + '"';
        JsonArrayRequest arrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                JSONObject jsonObject;
                try{
                    for(int i=0;i< response.length();i++){
                        Setlist conc=new Setlist();
                        jsonObject = response.getJSONObject(i);
                        data = jsonObject.getString("Data");
                        conc.setDate(data);
                        postoConcerto = jsonObject.getString("PostoConcerto");
                        conc.setVenueName(postoConcerto);
                        cittaConcerto = jsonObject.getString("CittaConcerto");
                        conc.setCity(cittaConcerto);
                        pseArtista=jsonObject.getString("PseArtista");
                        conc.setArtistName(pseArtista);
                        idConcerto=jsonObject.getString("IdConcerto");
                        conc.setId(idConcerto);
                        conc.setCover(ArtistaHomeFragment.immagine);
                        concerti.add(conc);
                    }
                    fragment.riempiArray(concerti);
                    dialog.hide();
                    startTransiction(fragment);

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

    public void startTransiction(Fragment fragment){

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack("").commit();
    }
    
}
