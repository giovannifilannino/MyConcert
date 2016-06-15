package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by Giovanni on 15/06/2016.
 */
public class ArtistaHome extends Fragment {

    private TextView nomeArtista;
    private TextView cognomeArtista;
    private TextView aliasArtista;



    private String nomeArtistaString;
    private String cognomeArtistaString;
    private String aliasArtistaString;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nomeArtista =(TextView) getActivity().findViewById(R.id.nomeArtistaID);
        cognomeArtista = (TextView) getActivity().findViewById(R.id.cognomeArtistaID);
        aliasArtista = (TextView) getActivity().findViewById(R.id.aliasArtist);

        nomeArtista.setText(nomeArtistaString);
        cognomeArtista.setText(cognomeArtistaString);
        aliasArtista.setText(aliasArtistaString);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artistahome, container, false);
    }

    public void setAliasArtistaString(String aliasArtistaString) {
        this.aliasArtistaString = aliasArtistaString;
    }

    public void setNomeArtistaString(String nomeArtistaString) {
        this.nomeArtistaString = nomeArtistaString;
    }

    public void setCognomeArtitaString(String cognomeArtistaString) {
        this.cognomeArtistaString = cognomeArtistaString;
    }
}
