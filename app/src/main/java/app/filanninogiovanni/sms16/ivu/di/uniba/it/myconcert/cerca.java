package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

public class cerca extends AppCompatActivity implements search_fragment.OnSearch{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private ListView listViewDrawerLayout;
    private String[] optionDrawer;
    private ResultFragment resultFragment = new ResultFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.irko);
        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //layout per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer); //lista del menu laterale

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

        fragmentManager = getFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

          Fragment search = new search_fragment();

          fragmentTransaction.replace(R.id.content_frame, search);
          fragmentTransaction.commit();
    }

    @Override
    public void searchStart(ArrayList<Setlist> urlDaCercare) {
        fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,resultFragment).commit();

        resultFragment.riempiArray(urlDaCercare);
    }
}
