package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

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
    private InputMethodManager mInputManager;
    private ArrayList<String> mTodoList;
    private ArrayAdapter mToDoAdapter;
    int defaultColor;

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
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        defaultColor = getResources().getColor(R.color.colorPrimaryDark);


        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        mList.setVisibility(View.GONE);
        isEditTextVisible = false;

        setUpAdapter();
        loadPlace();
        windowTransition();
        getPhoto();

    }

    private void setUpAdapter() {

        mList.setAdapter(new ArrayAdapter<String>(this,R.layout.itemsong,R.id.textSong,setlist));

    }

    private void loadPlace() {

        dataTXT.setText(data);

        nomeArtista.setText(nome);

    }

    private void windowTransition() {
        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                getWindow().getEnterTransition().removeListener(this);
                mList.setVisibility(View.VISIBLE);
            }
        });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mList.setVisibility(View.GONE);
    }

}