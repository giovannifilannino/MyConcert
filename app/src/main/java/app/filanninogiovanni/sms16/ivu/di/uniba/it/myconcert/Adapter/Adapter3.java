package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.DetailActivity2;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.ErrorClass;

/**
 * Created by delmi on 09/06/2016.
 */
public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {
    private String[] mDataset;
    LayoutInflater inflater;
    List<String> setlists;
    private static final String SUCCESS_TAG = "success";
    private static boolean  fatto=false;
    Setlist setList;
    private int layout;
    String idConerto;
    Context mcontext;
    private RequestQueue requestQueue;
    String URLAddCanzone="http://mymusiclive.altervista.org/AddSong.php?";
    String URLDeleteCanzone="http://mymusiclive.altervista.org/RemoveSong.php?";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView song;
        LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            song=(TextView) v.findViewById(R.id.textSong);
            linearLayout=(LinearLayout) v.findViewById(R.id.mainHolder);
        }




    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter3(Context context, int resource, ArrayList<String> objects,String idConcert) {
        inflater = LayoutInflater.from(context);
        layout = resource;
        setlists = objects;
        setlists.add(0,"Le tue canzoni");
        this.idConerto=idConcert;
        mcontext=context;
        requestQueue= Volley.newRequestQueue(context);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter3.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        View convertView=null;
        // create a new view
        switch (viewType){
            case 0:convertView = inflater.inflate(R.layout.songs_layout,parent,false);
                break;
            case 1:convertView = inflater.inflate(R.layout.itemsong,parent,false);
                break;
        }

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(convertView);

        return vh;
    }



    @Override
    public int getItemViewType(int position) {
        //Implement your logic here
        if(position==0){
            return 0;
        }
        else
            return 1;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.song.setText(setlists.get(position));

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return setlists.size();
    }

}
