package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public void riempiArray(ArrayList<Setlist> setListArrayList){
        this.setListArrayList = setListArrayList;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listItem = (ListView) getActivity().findViewById(R.id.listviewresult);
        setListAdapter = new SetListAdapter(getActivity(),R.layout.item_resultlistitem,setListArrayList);
        listItem.setAdapter(setListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.resultlistfragment,container,false);
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
