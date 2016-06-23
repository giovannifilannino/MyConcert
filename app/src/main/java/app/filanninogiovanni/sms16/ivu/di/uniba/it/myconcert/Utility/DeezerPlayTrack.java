package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility;

import android.app.Activity;
import android.content.Context;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

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

    private DeezerPlayTrack(Context c){
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

    public void PlaySong(final String track){

        requestListener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                List<Track> trackList = (List<Track>) o;
                idSong = trackList.get(0).getId();
            }

            @Override
            public void onUnparsedResult(String s, Object o) {

            }

            @Override
            public void onException(Exception e, Object o) {

            }
        };

        deezerRequest = DeezerRequestFactory.requestSearchTracks(track);
        deezerConnect.requestAsync(deezerRequest,requestListener);

        trackPlayer.playTrack(idSong);
    }

}
