package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Giovanni on 23/06/2016.
 */
public class DeezerPlayTrack {

    private static DeezerConnect deezerConnect;
    DeezerRequest deezerRequest;
    private static final String idDeezer = "182722";
    private static  DeezerPlayTrack deezerPlayTrack;
    private long idSong = 0;
    private RequestListener requestListener;
    private static TrackPlayer trackPlayer;
    private RequestID requestID;
    private static String query;

    private DeezerPlayTrack(Context c){
        requestID = new RequestID();
        deezerConnect = new DeezerConnect(c, idDeezer);
    }

    public static DeezerPlayTrack  getIstance(Activity context){
        deezerPlayTrack = new DeezerPlayTrack(context);
        try{

            trackPlayer = new TrackPlayer(context.getApplication(),deezerConnect, new WifiAndMobileNetworkStateChecker());
        } catch (Exception e){
            e.printStackTrace();
        }

        return deezerPlayTrack;
    }


    public void getID(String track) throws IOException, DeezerError{

        requestID.execute(track);
    }


    public void PlaySong(){
        long id= 0;
        try {
            JSONObject jsonObject = new JSONObject(query);
            id=jsonObject.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackPlayer.playTrack(id);
    }



    public void StopSong(){
        trackPlayer.stop();
    }

    private class RequestID extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            deezerRequest = DeezerRequestFactory.requestSearchTracks(params[0]);
            try {
                return deezerConnect.requestSync(deezerRequest);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DeezerError deezerError) {
                deezerError.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String aLong) {
            super.onPostExecute(aLong);
            query = aLong;
            requestID = new RequestID();
        }
    }

}
