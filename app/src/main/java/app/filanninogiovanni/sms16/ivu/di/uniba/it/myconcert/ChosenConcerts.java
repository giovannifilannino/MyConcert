package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.AdapterCardTweet;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;


public class ChosenConcerts extends Fragment {

    private String URL_GET_PARTECIPATION = "http://mymusiclive.altervista.org/getPartecipation.php?username=" + '"' + loginFragment.actualUsername + '"';
    private RecyclerView recyclerView;
    private ArrayList<Setlist> partecipationList = new ArrayList<Setlist>();
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chosen_concerts,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        Log.d("sss",URL_GET_PARTECIPATION);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_PARTECIPATION, new Response.Listener<JSONArray>() {
            JSONObject jsonObject;
            Setlist setlist = new Setlist();
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        setlist.setArtistName(jsonObject.getString("PseArtista"));
                        setlist.setDate(jsonObject.getString("Data"));
                        setlist.setHashTag(jsonObject.getString("HashTag"));
                        setlist.setCity(jsonObject.getString("CittaConcerto"));
                        setlist.setVenueName(jsonObject.getString("PostoConcerto"));
                        partecipationList.add(setlist);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_partecipation);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                AdapterCardTweet ca = new AdapterCardTweet(getActivity(), R.layout.card3, partecipationList);
                recyclerView.setAdapter(ca);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


}
