package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity implements app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment.OnLoginConfirmed{

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
        setContentView(R.layout.activity_main);

        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //layout per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer); //lista del menu laterale


        setSupportActionBar(toolbar);


        //inizializzazione del skd di facebook per il login
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        /*
        * Comportamento del drawer in apertura e chiusura
        * */
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                // creates call to onPrepareOptionsMenu()
            }
        };
        //collegamento comportamento e icona per la toolbar e drawer
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);


        listViewDrawerLayout.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,optionDrawer));

        //fragment registrazione //da sostituire con quella di login
        fragmentManager = getFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();

        loginFragment = new loginFragment();

        fragmentTransaction.add(R.id.content_frame, loginFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void goToSearchFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        search_fragmento = new search_fragment();
        fragmentTransaction.replace(R.id.content_frame,search_fragmento).commit();
    }
}
