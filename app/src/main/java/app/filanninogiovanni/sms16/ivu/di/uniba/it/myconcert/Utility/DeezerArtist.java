package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility;

import android.app.Activity;

import com.deezer.sdk.model.AImageOwner;
import com.deezer.sdk.model.Artist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.List;

/**
 * Created by Giovanni on 23/06/2016.
 */
public class DeezerArtist {

    private static DeezerConnect deezerConnect;
    private static DeezerRequest deezerRequest;
    private static RequestListener requestListener;

    private static String idDeezer = "182722";
    private static DeezerArtist deezerArtist;
    private static String urlCover = "";

    private DeezerArtist(Activity activity){
        deezerConnect = new DeezerConnect(activity, idDeezer);
    }

    public static DeezerArtist getIstance(Activity activity){
        deezerArtist = new DeezerArtist(activity);
        return  deezerArtist;
    }

    public String getURLCover(String artist){
        deezerRequest = DeezerRequestFactory.requestSearchArtists(artist);

        requestListener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                List<Artist> artistList = (List<Artist>) o;
                urlCover = artistList.get(0).getImageUrl(AImageOwner.ImageSize.big);
            }

            @Override
            public void onUnparsedResult(String s, Object o) {

            }

            @Override
            public void onException(Exception e, Object o) {

            }
        };

        deezerConnect.requestAsync(deezerRequest,requestListener);

        return  urlCover;
    }

}
