package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;


import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.android.volley.toolbox.Volley;

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
    private ArrayList<String> optionDrawer = new ArrayList<String>();
    FragmentManager fragmentManager;

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
    }
}
