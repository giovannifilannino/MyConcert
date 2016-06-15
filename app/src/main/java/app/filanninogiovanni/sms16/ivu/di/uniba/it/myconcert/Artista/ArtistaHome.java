package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by Giovanni on 15/06/2016.
 */
public class ArtistaHome extends Fragment
{

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artistahome, container, false);
    }
}
