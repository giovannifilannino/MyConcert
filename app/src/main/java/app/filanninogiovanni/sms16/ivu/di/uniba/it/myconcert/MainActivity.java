package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.PagerAdapter;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment.OnLoginConfirmed{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.



    private DrawerLayout drawerLayout;
    private ListView listViewDrawerLayout;
    private String[] optionDrawer;
    private loginFragment loginFragment;
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private search_fragment search_fragmento;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private static final String TWITTER_KEY = "9R1qMlXL3qRX4wwkKasPn6yvE";
    private static final String TWITTER_SECRET = "kTZ7Z9aU0b04igbUAp12AjgR0tcXXnHvPVc90E0t6aRUx5bh24";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        PagerAdapter pagerAdapter = (PagerAdapter) viewPager.getAdapter();
        int index = viewPager.getCurrentItem();
        Fragment fragment = pagerAdapter.getFragment(index);
        Log.d("je murt d'allah",fragment.toString());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


    }

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

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
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
    public void goToSearchFragment() {
        Intent goToCerca = new Intent(this,cerca.class);
        startActivity(goToCerca);
    }


}
