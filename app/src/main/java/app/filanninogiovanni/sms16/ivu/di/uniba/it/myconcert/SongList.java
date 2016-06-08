package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by Giovanni on 07/06/2016.
 */
public class SongList extends Fragment{

    public static ArrayList<String> songs;
    private XMLSetListParser xmlSongParser = new XMLSetListParser();
    private  String URL_ARTIST = "http://api.setlist.fm/rest/0.1/search/setlists?artistName=";
    private ListView listSong;

    public void riempiArray(ArrayList<String> id){

        songs = id;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listSong = (ListView) getActivity().findViewById(R.id.musicList);
        listSong.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.itemsong,R.id.textSong,songs));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.songlist,container,false);
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
