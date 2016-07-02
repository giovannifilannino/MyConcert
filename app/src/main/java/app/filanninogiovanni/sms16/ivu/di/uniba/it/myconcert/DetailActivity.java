package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter.ItemSongPlayAdapter;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Song;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.DeezerPlayTrack;

public class DetailActivity extends Activity implements View.OnClickListener {

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
    private ImageButton partecipero;
    private ImageButton editSongList;
    private InputMethodManager mInputManager;
    private ArrayList<String> mTodoList;
    private ArrayAdapter mToDoAdapter;
    private ArrayList<Song> songArrayList;
    int defaultColor;
    int color;
    private static final String SUCCESS_TAG = "success";
    private RequestQueue requestQueue;
    private ItemSongPlayAdapter itemSongPlayAdapter;
    private boolean partecipation=false;
    private boolean visible=false;

    private static String URL = "http://mymusiclive.altervista.org/setPartecipation.php?username=" + '"' + loginFragment.actualUsername + '"';

    private DeezerPlayTrack deezerPlayTrack;


    private View.OnClickListener setPartecipation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!partecipation){
                partecipero.setImageResource(R.drawable.thumbsup_selected);
                partecipation=true;
                editSongList.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                JsonObjectRequest arrayRequest = new JsonObjectRequest(URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject test = response;
                        /*
                        try {
                            String success = test.get(SUCCESS_TAG).toString();
                            if(success.compareTo("1")==0){
                                Log.d("partecipera","partecipera");
                                editSongList.setVisibility(View.VISIBLE);
                            } else {
                                Log.d("partecipera"," non partecipera");
                                editSongList.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        */

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                });
                requestQueue.add(arrayRequest);
            } else {
                partecipation=false;
                partecipero.setImageResource(R.drawable.thumbsup);
                editSongList.setVisibility(View.GONE);
                itemSongPlayAdapter.setGone();
                visible=false;
            }

        }
    };

    private View.OnClickListener editSong = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!visible) {
                itemSongPlayAdapter.setVisible();
                visible=true;
            }else {
                itemSongPlayAdapter.setGone();
                visible=false;
            }
            Log.d("Wanna","Chi sei goku non lo sai");
        }
    };

    private ListView.OnItemLongClickListener editSongs = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);

        setlist = getIntent().getStringArrayListExtra("canzoni");
        nome=getIntent().getStringExtra("cantante");
        data=getIntent().getStringExtra("data");
        mList = (ListView) findViewById(R.id.list);
        mImageView = (ImageView) findViewById(R.id.placeImage);
        nomeArtista = (TextView) findViewById(R.id.artistaDett);
        dataTXT=(TextView) findViewById(R.id.dataDett);
        partecipero = (ImageButton) findViewById(R.id.partecipero);
        editSongList = (ImageButton) findViewById(R.id.editSongList);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        defaultColor = getResources().getColor(R.color.colorPrimaryDark);
        mTitleHolder.setBackgroundColor(color);
        final Context context=this;
        Transition fade = new Fade();
        songArrayList = getSongArray(nome,setlist);
        setUpAdapter();
        requestQueue = Volley.newRequestQueue(this);
        partecipero.setOnClickListener(setPartecipation);
        editSongList.setOnClickListener(editSong);

        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        if(ResultFragment.bitmap!=null) {
            mImageView.setImageBitmap(ResultFragment.bitmap);
            Palette.from(ResultFragment.bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int bgColor = palette.getLightMutedColor(context.getResources().getColor(R.color.colorPrimary));
                    mTitleHolder.setBackgroundColor(bgColor);
                }
            });
        }
        else {
            Bitmap bit= BitmapFactory.decodeResource(getResources(),R.drawable.concertimilano);
            mImageView.setImageBitmap(bit);
            Palette.from(bit).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int bgColor = palette.getLightMutedColor(context.getResources().getColor(R.color.colorPrimary));
                    mTitleHolder.setBackgroundColor(bgColor);
                }
            });
        }
        isEditTextVisible = false;fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);
        loadPlace();
    }

    private void setUpAdapter() {
        itemSongPlayAdapter = new ItemSongPlayAdapter(this,R.layout.itemplaysong,songArrayList);
        mList.setAdapter(itemSongPlayAdapter);

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

    private ArrayList<Song> getSongArray(String artist, ArrayList<String> songs){
        ArrayList<Song> songArray = new ArrayList<Song>();
        for(String s: songs){
            songArray.add(new Song(s,artist));
        }

        return songArray;
    }

}