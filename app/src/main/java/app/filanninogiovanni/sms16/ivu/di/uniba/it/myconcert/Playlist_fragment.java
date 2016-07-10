package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.MyAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.PlaylistAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

import static app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ResultFragment.bitmap;

/**
 * Created by delmi on 08/07/2016.
 */

public class Playlist_fragment extends Fragment {

    private RequestQueue requestQueue;
    ArrayList<Setlist> setLists = new ArrayList<Setlist>();
    Setlist setlist;
    private RecyclerView recyclerView;
    ArrayList<String> canzoni=new ArrayList<String>();




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
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            JSONObject jsonObject;

            @Override
            public void onResponse(JSONArray response) {

                for(int i=0; i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Setlist setlist = new Setlist();
                        setlist.setDate(jsonObject.getString("DataConcerto"));
                        setlist.setArtistName(jsonObject.getString("PseArtista"));
                        setlist.setId(jsonObject.getString("idConcerto"));
                        setLists.add(i,setlist);
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
                PlaylistAdapter.OnItemClickListener onItemClickListener= new PlaylistAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position, Setlist setlist) {


                        Intent intent = new Intent(getActivity(), DetailActivity3.class);
                        intent.putExtra("cantante",setlist.getArtistName());
                        intent.putExtra("data",setlist.getDate());
                        intent.putExtra("id",setlist.getId());
                        intent.putExtra("citta",setlist.getCity());
                        intent.putExtra("luogo",setlist.getVenueName());
                        bitmap=setlist.getCover();
                        ImageView placeImage = (ImageView) v.findViewById(R.id.placeImage);
                        LinearLayout placeNameHolder = (LinearLayout) v.findViewById(R.id.placeNameHolder);
                        View navigationBar = getActivity().findViewById(android.R.id.navigationBarBackground);
                        Pair<View, String> navbar =Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                        Pair<View, String> imagePair = Pair.create((View ) placeImage, "tImage");
                        Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
                        ActivityOptionsCompat options;
                        if(navbar==null) {
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                    imagePair, holderPair, navbar);
                        }
                        else {
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                    imagePair, holderPair);
                        }
                        caricaCanzoni(position,intent,options);
                        canzoni.clear();

                    }
                };
                ca.setOnItemClickListener(onItemClickListener);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
        setLists.clear();
    }

    public void caricaCanzoni(int position, final Intent intent, final ActivityOptionsCompat options){
        String idConcerto=setLists.get(position).getId();


        String url="http://mymusiclive.altervista.org/getlistacanzoniPlaylist.php?id="+'"'+idConcerto+ '"' +"&Username=" + '"'+loginFragment.actualUsername +'"';
        Log.d("URL","" + url);
        JsonArrayRequest arrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                try{
                    for(int i=0;i< response.length();i++){
                        jsonObject = response.getJSONObject(i);
                        canzoni.add( jsonObject.getString("NomeCanzone"));

                    }
                    intent.putStringArrayListExtra("canzoni", canzoni);
                    startActivity(intent, options.toBundle());

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


    @Override
    public void onPause() {
        super.onPause();
    }


}
