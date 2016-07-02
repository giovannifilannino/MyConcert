package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.SearchResultOrder;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.Player;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Giovanni on 23/06/2016.
 */
public class DeezerPlayTrack {

    private static DeezerConnect deezerConnect;
    DeezerRequest deezerRequest;
    private static final String idDeezer = "182722";
    private static  DeezerPlayTrack deezerPlayTrack;
    private long idSong = 0;
    private String artistName = "";
    private static TrackPlayer trackPlayer;

    private Thread playSong;
    private playTrackRun runnableTrack;
    private SetID setID;
    private Thread setIDT;

    private Context c;

    private DeezerPlayTrack(Context c){
        deezerConnect = new DeezerConnect(c, idDeezer);
        runnableTrack = new playTrackRun();
        setID = new SetID();
        this.c = c;
    }

    public static DeezerPlayTrack  getIstance(Activity context){
        deezerPlayTrack = new DeezerPlayTrack(context);
        setupTrakPlayer(context);

        return deezerPlayTrack;
    }

    private static  void setupTrakPlayer(Activity activity){
        try{

            trackPlayer = new TrackPlayer(activity.getApplication(),deezerConnect, new WifiAndMobileNetworkStateChecker());
        } catch (Exception e){
            e.printStackTrace();
        }
    }




    public void PlaySong(String track, String artist){
        deezerRequest = DeezerRequestFactory.requestSearchTracks(track, SearchResultOrder.Ranking);
        this.artistName = artist;
        setIDT = new Thread(setID);
        setIDT.start();
        try {
            playSong.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playSong = new Thread(runnableTrack);
        playSong.start();
    }

    private class playTrackRun implements Runnable{

        @Override
        public void run() {
            trackPlayer.playTrack(idSong);
        }
    }

    private class SetID implements Runnable{
        @Override
        public void run() {
            String result;
            try {
                result = deezerConnect.requestSync(deezerRequest);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject songObject = jsonArray.getJSONObject(i);
                    JSONObject artista = songObject.getJSONObject("artist");
                    String artistaName = artista.getString("name");
                    Log.d("mlml",artistaName);
                    if(artistaName.compareToIgnoreCase(artistName)==0) {
                        idSong = songObject.getLong("id");
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (DeezerError deezerError) {
                deezerError.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }




    public void StopSong(){
        trackPlayer.stop();
    }



}
