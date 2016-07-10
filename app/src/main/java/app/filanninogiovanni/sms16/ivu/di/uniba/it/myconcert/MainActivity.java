package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;



import android.app.FragmentManager;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.PagerAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista.ArtistaHome;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.ErrorClass;
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

    private TwitterAuthClient twitterAuthClient;

    private static final String TWITTER_KEY = "9R1qMlXL3qRX4wwkKasPn6yvE";
    private static final String TWITTER_SECRET = "kTZ7Z9aU0b04igbUAp12AjgR0tcXXnHvPVc90E0t6aRUx5bh24";

    private String URL = "http://mymusiclive.altervista.org/checkUtente.php?username=";
    private static final String SUCCESS_TAG = "success";
    private RequestQueue requestQueue;
    private String URLRegistration = "http://mymusiclive.altervista.org/registration.php?";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterAuthClient.onActivityResult(requestCode,resultCode,data

        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        requestQueue = Volley.newRequestQueue(this);
        Fabric.with(this, new Twitter(authConfig));
        Fabric.with(this, new TwitterCore(authConfig));
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        setContentView(R.layout.activity_main);
        twitterAuthClient = new TwitterAuthClient();
        optionDrawer = getResources().getStringArray(R.array.opzioni); //opzioni del menu laterale
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //information per la comparsa del menu laterale
        listViewDrawerLayout = (ListView) findViewById(R.id.left_drawer); //lista del menu laterale

        setSupportActionBar(toolbar);

       fragmentManager = getFragmentManager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.login));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.signup));
        tabLayout.setTabTextColors(Color.WHITE,R.color.colorAccent);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
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
    public void goToSearchFragment(String nome, String cognome) {
        Intent goToCerca = new Intent(this,cerca.class);
        goToCerca.putExtra("nome",nome);
        goToCerca.putExtra("cognome",cognome);
        startActivity(goToCerca);
        finish();
    }

    @Override
    public void loginTwitter() {

        twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
            JSONObject jsonObject = new JSONObject();
            @Override
            public void success(Result<TwitterSession> result) {
                URL = URL + '"'+result.data.getUserName()+'"';
                final String user = result.data.getUserName();
                JsonObjectRequest arrayRequest = new JsonObjectRequest(URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject test = response;
                        try {
                            String success = test.getString(SUCCESS_TAG);


                            if(success.compareTo("1")==0){
                                loginFragment.setActualUsername(user);
                                goToSearchFragment(user,"");
                            } else {
                                RegistrationAndLogin(user);
                            }
                        } catch (JSONException e) {
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

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    private void RegistrationAndLogin(final String user){
        JSONObject jsonObject = new JSONObject();
        loginFragment.setActualUsername(user);
        URLRegistration += "&nome=" + user  + "&cognome="  + "" + "&username=" + user+ "&password=" + "";
        JsonObjectRequest arrayRequest = new JsonObjectRequest(URLRegistration, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject test = response;
                try {
                    String success = test.get(SUCCESS_TAG).toString();
                    if(success.compareTo("1")==0){

                        goToSearchFragment(user,"");
                    } else {

                    }
                } catch (JSONException e) {
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
}
