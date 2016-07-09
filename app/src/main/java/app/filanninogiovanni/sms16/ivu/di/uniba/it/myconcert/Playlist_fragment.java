package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.AdapterCardTweet;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.PlaylistAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by delmi on 08/07/2016.
 */

public class Playlist_fragment extends Fragment {

    private RequestQueue requestQueue;
    ArrayList<Setlist> setLists = new ArrayList<Setlist>();
    Setlist setlist;
    private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chosen_concerts,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        String URL="http://mymusiclive.altervista.org/getPlaylist.php?&user=" + '"'+loginFragment.actualUsername+'"';
        Log.d("HAi ","" +URL);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            JSONObject jsonObject;
            Setlist setlist = new Setlist();
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        setlist.setDate(jsonObject.getString("DataConcerto"));
                        setlist.setArtistName(jsonObject.getString("PseArtista"));
                        setLists.add(setlist);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_partecipation);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                PlaylistAdapter ca = new PlaylistAdapter(getActivity(), R.layout.card2, setLists);
                recyclerView.setAdapter(ca);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        setLists=new ArrayList<>();
    }


}
