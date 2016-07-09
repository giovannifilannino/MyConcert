package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by delmi on 09/06/2016.
 */
public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<String> canz;
    OnItemClickListener mItemClickListener;
    Context mContext;



    ImageView covertArtist;
    private int layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView canzone;
        public ViewHolder(View v) {
            super(v);

            canzone = (TextView) v.findViewById(R.id.textSong);

        }


    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,Setlist setlist);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter3(Context context, int resource, ArrayList<String> canz) {
        inflater = LayoutInflater.from(context);
        mContext=context;
        this.canz=canz;
        layout = resource;
    }

    // Create new views (invoked by the information manager)
    @Override
    public Adapter3.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View convertView = inflater.inflate(layout,parent,false);
        // set the view's size, margins, paddings and information parameters
        ViewHolder vh = new ViewHolder(convertView);

        return vh;
    }


    // Replace the contents of a view (invoked by the information manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.canzone.setText(canz.get(position));

    }


    // Return the size of your dataset (invoked by the information manager)
    @Override
    public int getItemCount() {
        return canz.size();
    }
}
