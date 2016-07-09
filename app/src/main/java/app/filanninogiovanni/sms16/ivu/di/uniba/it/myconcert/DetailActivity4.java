package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.TrackPlayer;

import org.json.JSONObject;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.Adapter3;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Artista.ArtistaHomeFragment;


public class DetailActivity4 extends Activity implements View.OnClickListener {

    public static final String EXTRA_PARAM_ID = "place_id";
    private ListView mList;
    private ImageView mImageView;
    private TextView nomeArtista;
    private TextView dataTXT;
    private LinearLayout mTitleHolder;
    private ArrayList<String> setlist;
    private LinearLayout mRevealView;
    private EditText mEditTextTodo;
    private String nome;
    private String cit;
    private String lu;
    private TextView citta;
    private TextView luogo;
    private String data;
    private boolean isEditTextVisible;
    private InputMethodManager mInputManager;
    private ArrayList<String> mTodoList;
    private ArrayAdapter mToDoAdapter;
    int defaultColor;
    private EditText addcanzone;
    ImageButton image;
    private DeezerConnect deezerConnect;
    private AlbumPlayer albumPlayer;
    private Application application;
    private long albumID = 89142;
    private TrackPlayer trackPlayer;
    JSONObject jsonObject = new JSONObject(); //sta qui per debug
    private long idSong;
    private String URLCover;
    String idConcerto;
    private RequestQueue requestQueue;


    private String queryTrack = "https://api.deezer.com/search?q=track:";

    private DeezerRequest deezerRequest;
    private RequestListener requestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail_best_songs);
        final Context context = this;
        setlist = getIntent().getStringArrayListExtra("canzoni");
        nome = getIntent().getStringExtra("cantante");
        data = getIntent().getStringExtra("data");
        cit=getIntent().getStringExtra("citta");
        lu=getIntent().getStringExtra("luogo");
        idConcerto = getIntent().getStringExtra("id");
        //mList = (ListView) findViewById(R.id.list);
        mImageView = (ImageView) findViewById(R.id.placeImage);
        nomeArtista = (TextView) findViewById(R.id.artistaDett);
        citta = (TextView) findViewById(R.id.cittaDett);
        luogo = (TextView) findViewById(R.id.luogoDett);
        dataTXT = (TextView) findViewById(R.id.dataDett);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        defaultColor = getResources().getColor(R.color.colorPrimaryDark);
        requestQueue = Volley.newRequestQueue(this);
        final RecyclerView recList = (RecyclerView) findViewById(R.id.list);
        //recList.addItemDecoration(new LineItemDecoration());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        final Adapter3 ca = new Adapter3(this, R.layout.item_best_songs, setlist);
        mImageView.setImageBitmap(ArtistaHomeFragment.immagine);
        Palette palette = Palette.generate(ArtistaHomeFragment.immagine);
        int standard=getResources().getColor(R.color.colorPrimary);
        int vibrant = palette.getVibrantColor(standard);
        mTitleHolder.setBackgroundColor(vibrant);

        recList.setAdapter(ca);


        Transition fade = new Fade();


        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);
        citta.setVisibility(View.GONE);
        luogo.setVisibility(View.GONE);
        loadPlace();
    }



    private void loadPlace() {

        dataTXT.setText(data);
        citta.setText(cit);
        luogo.setText(lu);
        nomeArtista.setText(nome);
        citta.setVisibility(View.VISIBLE);
        luogo.setVisibility(View.VISIBLE);

    }

    private void windowTransition() {

    }

    @Override
    public void onClick(View v) {
    }




    @Override
    public void onBackPressed() {
        citta.setVisibility(View.GONE);
        luogo.setVisibility(View.GONE);
        super.onBackPressed();
    }
}