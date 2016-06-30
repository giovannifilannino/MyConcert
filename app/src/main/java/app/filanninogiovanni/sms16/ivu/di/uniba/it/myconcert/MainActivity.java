package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity implements app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment.OnLoginConfirmed{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "6P9f6p5KQseT5x36RhGRpjNTc";
    private static final String TWITTER_SECRET = "	z5zlsXOTGH9dYI6iRa7BTnaAeGxGpDJHjm4UewCG8DUqQiePV3";


    private DrawerLayout drawerLayout;
    private ListView listViewDrawerLayout;
    private String[] optionDrawer;
    private loginFragment loginFragment;
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private search_fragment search_fragmento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //information per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer); //lista del menu laterale

        setSupportActionBar(toolbar);




        //inizializzazione del skd di facebook per il login
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        /*
        * Comportamento del drawer in apertura e chiusura
        * */


        //fragment registrazione //da sostituire con quella di login
       fragmentManager = getFragmentManager();
       // FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();

      //  loginFragment = new loginFragment();

      //  fragmentTransaction.add(R.id.content_frame, loginFragment);
      //  fragmentTransaction.commit();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.login));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.signup));
        tabLayout.setTabTextColors(Color.WHITE,R.color.colorAccent);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void goToSearchFragment(String nome, String cognome, String alias,String urlImmagine, int value) {
        Intent intent = new Intent(this, cerca.class);
        intent.putExtra("artista",value);
        intent.putExtra("nomeArtista", nome);
        intent.putExtra("cognomeArtista", cognome);
        intent.putExtra("aliasArtista", alias);
        intent.putExtra("urlImmagine",urlImmagine);
        startActivity(intent);
        this.finish();
    }


}
