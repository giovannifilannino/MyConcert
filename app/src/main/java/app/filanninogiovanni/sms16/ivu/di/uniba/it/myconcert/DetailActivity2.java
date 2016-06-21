package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.Adapter2;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.MyAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class DetailActivity2 extends Activity implements View.OnClickListener {

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
    private String data;
    private boolean isEditTextVisible;
    private InputMethodManager mInputManager;
    private ArrayList<String> mTodoList;
    private ArrayAdapter mToDoAdapter;
    int defaultColor;

    private DeezerConnect deezerConnect;
    private AlbumPlayer albumPlayer;
    private Application application;
    private long albumID = 89142;
    private TrackPlayer trackPlayer;
    JSONObject jsonObject = new JSONObject(); //sta qui per debug
    private long idSong;
    private String URLCover;
    private String queryTrack = "https://api.deezer.com/search?q=track:";
    private RequestQueue requestQueue;

    private DeezerRequest deezerRequest;
    private RequestListener requestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail2);
        application = getApplication();


        setlist = getIntent().getStringArrayListExtra("canzoni");
        nome=getIntent().getStringExtra("cantante");
        data=getIntent().getStringExtra("data");
        //mList = (ListView) findViewById(R.id.list);
        mImageView = (ImageView) findViewById(R.id.placeImage);
        nomeArtista = (TextView) findViewById(R.id.artistaDett);
        dataTXT=(TextView) findViewById(R.id.dataDett);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        defaultColor = getResources().getColor(R.color.colorPrimaryDark);

        final RecyclerView recList=(RecyclerView)findViewById(R.id.list);
        OvershootInLeftAnimator animator=new OvershootInLeftAnimator();
        animator.setAddDuration(2000);
        animator.setRemoveDuration(2000);
        recList.setItemAnimator(animator);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        final Adapter2 ca = new Adapter2(this, R.layout.itemsong, setlist);
        recList.setAdapter(ca);
        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ca.addItem(1,"ciao");

            }
        });

        Transition fade = new Fade();


        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);
    }

    private void setUpAdapter() {

        mList.setAdapter(new ArrayAdapter<String>(this,R.layout.itemsong,R.id.textSong,setlist));

    }

    private void loadPlace() {

        dataTXT.setText(data);

        nomeArtista.setText(nome);

    }

    private void windowTransition() {

    }

    private void addToDo(String todo) {
        mTodoList.add(todo);
    }

    private void getPhoto() {

    }

    private void colorize(Bitmap photo) {
    }

    private void applyPalette() {

    }

    @Override
    public void onClick(View v) {
    }

    private void revealEditText(LinearLayout view) {

    }

    private void hideEditText(final LinearLayout view) {

    }


}