package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.MyAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.SetListAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class ResultFragment extends Fragment {

    public static Bitmap bitmap;
    ArrayList<Setlist> setListArrayList;

    private String URL_CANZONI_CONCERTO = "http://mymusiclive.altervista.org/canzoniConcerto.php?id=";
    private ArrayList songs = new ArrayList();
    private RequestQueue requestQueue;



    public void riempiArray(ArrayList<Setlist> setListArrayList){
        this.setListArrayList = setListArrayList;
    }



    MyAdapter.OnItemClickListener onItemClickListener = new MyAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position, Setlist setlist) {

            final String artistName = setlist.getArtistName();
            final String idM = setlist.getId();
            final String dataM = setlist.getDate();
            final Bitmap bitmapM = setlist.getCover();
            final String citta=setlist.getCity();
            final String luogo=setlist.getVenueName();
            String url = URL_CANZONI_CONCERTO + idM;
            final View v = view;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject;
                    for(int i=0; i<response.length(); i++){
                        try {
                            jsonObject = response.getJSONObject(i);
                            songs.add(jsonObject.getString("TitoloCanzone"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    if(songs.size()!=0) {
                        if(intent.hasExtra("canzoni")){
                            intent.removeExtra("canzoni");
                            intent.putStringArrayListExtra("canzoni", songs);
                        } else {
                            intent.putStringArrayListExtra("canzoni", songs);
                        }

                    } else {
                        intent.putStringArrayListExtra("canzoni",setListArrayList.get(position).getSongs());
                    }
                    intent.putExtra("cantante",artistName);
                    intent.putExtra("data",dataM);
                    intent.putExtra("id",idM);
                    intent.putExtra("citta",citta);
                    intent.putExtra("luogo",luogo);

                    bitmap=bitmapM;
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
                    startActivity(intent, options.toBundle());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(jsonArrayRequest);
        }

    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recList=(RecyclerView)getActivity().findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        requestQueue = Volley.newRequestQueue(getActivity());
        recList.setLayoutManager(llm);
        MyAdapter ca = new MyAdapter(getActivity(), R.layout.card2, setListArrayList);
        recList.setAdapter(ca);
        ca.setOnItemClickListener(onItemClickListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        songs = new ArrayList();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista,container,false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }





}
