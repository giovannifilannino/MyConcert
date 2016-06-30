package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;


import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.util.Pair;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.MyAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.SetListAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.DetailActivity;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.DetailActivity2;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class ResultFragmentArtisti extends Fragment {

    public static Bitmap bitmap;
    ListView listItem;
    SetListAdapter setListAdapter;
    ArrayList<String> canzoni=new ArrayList<String>();
    ArrayList<Setlist> setListArrayList;
    private static OnSetListSelecter onSetListSelecter;
    private Setlist dacaricare;
    RequestQueue requestQueue;
    ProgressDialog dialog;
    String urlPHPpart = "http://mymusiclive.altervista.org/canzoniConcerto.php?id=";
    public void riempiArray(ArrayList<Setlist> setListArrayList){
        this.setListArrayList = setListArrayList;
    }

    public interface OnSetListSelecter{
        public void showSongs(ArrayList<String> songs, boolean songsavaible);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView recList=(RecyclerView)getActivity().findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        requestQueue = Volley.newRequestQueue(getActivity());
        final MyAdapter ca = new MyAdapter(getActivity(), R.layout.card2, setListArrayList);
        recList.setAdapter(ca);
        MyAdapter.OnItemClickListener onItemClickListener= new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position,Setlist setlist) {

                Intent intent = new Intent(getActivity(), DetailActivity2.class);
                intent.putExtra("cantante",setlist.getArtistName());
                intent.putExtra("data",setlist.getDate());
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

            }
        };
        ca.setOnItemClickListener(onItemClickListener);
        canzoni.clear();
        FloatingActionButton floatingActionButton=(FloatingActionButton)getActivity().findViewById(R.id.addconcerto);
        final Setlist prova=new Setlist();
        prova.setArtistName(setListArrayList.get(0).getArtistName());
        prova.setCover(setListArrayList.get(0).getCover());
        prova.setCity("bari");
        prova.setDate("22/10/2015");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ca.addItem(1,prova);
                    recList.scrollToPosition(0);
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_concerti_artista,container,false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void caricaCanzoni(int position, final Intent intent, final ActivityOptionsCompat options){
        String id=setListArrayList.get(position).getId();
        String url=urlPHPpart+id;
        JsonArrayRequest arrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                JSONObject jsonObject;
                try{
                    for(int i=0;i< response.length();i++){
                        jsonObject = response.getJSONObject(i);
                        canzoni.add( jsonObject.getString("TitoloCanzone"));

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





}
