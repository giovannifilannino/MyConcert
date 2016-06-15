package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.util.ArrayList;

public class DetailActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_PARAM_ID = "place_id";
    private ListView mList;
    private ImageView mImageView;
    private TextView mTitle;
    private LinearLayout mTitleHolder;
    private Place mPlace;
    private LinearLayout mRevealView;
    private EditText mEditTextTodo;
    private boolean isEditTextVisible;
    private InputMethodManager mInputManager;
    private ArrayList<String> mTodoList;
    private ArrayAdapter mToDoAdapter;
    int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);

        mPlace = PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
        mList = (ListView) findViewById(R.id.list);
        mImageView = (ImageView) findViewById(R.id.placeImage);
        mTitle = (TextView) findViewById(R.id.textView);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        defaultColor = getResources().getColor(R.color.colorPrimaryDark);

        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;

        setUpAdapter();
        loadPlace();
        windowTransition();
        getPhoto();
    }

    private void setUpAdapter() {

    }

    private void loadPlace() {

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