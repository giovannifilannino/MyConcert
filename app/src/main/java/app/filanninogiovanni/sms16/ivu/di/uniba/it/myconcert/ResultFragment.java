package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class ResultFragment extends Fragment {

    ListView listItem;
    SetListAdapter setListAdapter;
    ArrayList<Setlist> setListArrayList;
    private static OnSetListSelecter onSetListSelecter;
    private Setlist dacaricare;

    public void riempiArray(ArrayList<Setlist> setListArrayList){
        this.setListArrayList = setListArrayList;
    }

    public interface OnSetListSelecter{
        public void showSongs(ArrayList<String> songs, boolean songsavaible);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recList=(RecyclerView)getActivity().findViewById(R.id.rv);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        MyAdapter ca =new MyAdapter(getActivity(),R.layout.card,setListArrayList);
        recList.setAdapter(ca);
        /*
        listItem = (ListView) getActivity().findViewById(R.id.listviewresult);
        setListAdapter = new SetListAdapter(getActivity(),R.layout.item_resultlistitem,setListArrayList);
        listItem.setAdapter(setListAdapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dacaricare = setListArrayList.get(position);
                boolean avaible = dacaricare.getSongs().size() != 0;
                onSetListSelecter.showSongs(dacaricare.getSongs(),avaible);
            }
        });*/
    }

    public static void click(ArrayList<String> songs){
        boolean avaible = songs.size() != 0;
        onSetListSelecter.showSongs(songs,avaible);
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
        onSetListSelecter = (OnSetListSelecter) context;
    }



}
