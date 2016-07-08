package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by delmi on 08/07/2016.
 */

public class Playlist_fragment extends Fragment {
    private ArrayList Data = new ArrayList();
    private ArrayList Artista = new ArrayList();
    private RequestQueue requestQueue;
    ArrayList<Setlist> setLists = new ArrayList<Setlist>();
    Setlist setlist;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        requestQueue = Volley.newRequestQueue(getActivity());
        String URL="http://mymusiclive.altervista.org/getPlaylist.php?&user=" + '"'+loginFragment.actualUsername+'"';
        Log.d("HAi ","" +URL);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for(int i=0; i<response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Data.add(jsonObject.getString("NomeCanzone"));
                        Artista.add(jsonObject.getString("PseArtista"));

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HAi ","errorresp");

            }


        });
        requestQueue.add(jsonArrayRequest);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_fragment,container,false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
