package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.BigScreenUtility;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.SearchMetadata;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.Arrays;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista.ArtistaHome;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista.ArtistaHomeFragment;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by Giovanni on 30/06/2016.
 */
public class TwitterList extends ListFragment {

    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String query = "#" + ArtistaHomeFragment.HashTag;
        

        SearchTimeline searchTimeline = new SearchTimeline.Builder().query(query).build();
        TweetTimelineListAdapter tweetTimelineListAdapter = new TweetTimelineListAdapter.Builder(getActivity()).setTimeline(searchTimeline).build();

        setListAdapter(tweetTimelineListAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.twitterview,container,false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar_artista);
        toolbar.setTitle("I VOSTRI TWEET");
        return view;
    }
}
