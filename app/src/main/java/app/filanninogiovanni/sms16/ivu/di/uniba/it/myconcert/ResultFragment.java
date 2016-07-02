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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.util.Pair;
import java.util.ArrayList;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.MyAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.SetListAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class ResultFragment extends Fragment {

    public static Bitmap bitmap;
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
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
            MyAdapter ca = new MyAdapter(getActivity(), R.layout.card2, setListArrayList);
            recList.setAdapter(ca);
            MyAdapter.OnItemClickListener onItemClickListener= new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position,Setlist setlist) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putStringArrayListExtra("canzoni", setlist.getSongs());
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
                    startActivity(intent, options.toBundle());

                }
            };
        ca.setOnItemClickListener(onItemClickListener);

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
