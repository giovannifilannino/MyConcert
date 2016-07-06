package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.loginFragment;

/**
 * Created by delmi on 09/06/2016.
 */
public class AdapterCardTweet extends RecyclerView.Adapter<AdapterCardTweet.ViewHolder> {
    private String[] mDataset;
    LayoutInflater inflater;
    List<Setlist> setlists;
    OnItemClickListener mItemClickListener;
    Setlist setList;
    Context mContext;
    int colore;
    TwitterSession session;
    ImageView covertArtist;
    private int layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView nameArtist;
        //  TextView nameVenue;
        //TextView nameCity;
        TextView date;
        LinearLayout barra;
        ImageView coverArtista;
        FloatingActionButton floatingActionButton;
        // CardView cardView;
        LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            nameArtist=(TextView) v.findViewById(R.id.artista);
            // nameVenue = (TextView) v.findViewById(R.id.textNameVenueItemList);
            // nameCity = (TextView) v.findViewById(R.id.textNameCityItemList);
            date = (TextView) v.findViewById(R.id.data);
            barra=(LinearLayout)v.findViewById(R.id.placeNameHolder);
            floatingActionButton=(FloatingActionButton) v.findViewById(R.id.tweetIT);
            //cardView=(CardView)v.findViewById(R.id.cv);
            coverArtista = (ImageView) v.findViewById(R.id.placeImage);
            linearLayout=(LinearLayout) v.findViewById(R.id.mainHolder);
            linearLayout.setOnClickListener(this);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TwitterSession session = loginFragment.twitterSession;
                    final Intent intent = new ComposerActivity.Builder(mContext)
                            .session(session)
                            .hashtags("#aaaaa")
                            .createIntent();
                    mContext.startActivity(intent);
                }
            });

            floatingActionButton.setImageResource(R.drawable.tweeticon2);

        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition(), setlists.get( getPosition()));
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,Setlist setlist);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterCardTweet(Context context, int resource, List<Setlist> objects) {
        inflater = LayoutInflater.from(context);
        mContext=context;
        layout = resource;
        setlists = objects;
    }

    // Create new views (invoked by the information manager)
    @Override
    public AdapterCardTweet.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        setList = setlists.get(position);
        holder.nameArtist.setText(setList.getArtistName());
        //holder.nameVenue.setText(setList.getVenueName());
        // holder.nameCity.setText(setList.getCity());
        holder.date.setText(setList.getDate());

        if (setList.getCover() != null) {
            Palette palette = Palette.generate(setList.getCover());
            int standard=mContext.getResources().getColor(R.color.colorPrimary);
            int vibrant = palette.getVibrantColor(standard);
            colore = vibrant ;
            holder.barra.setBackgroundColor(vibrant);
            holder.coverArtista.setImageBitmap(setList.getCover());

        }
        else {
            Bitmap bit=BitmapFactory.decodeResource(mContext.getResources(),R.drawable.concertimilano);
            Palette palette = Palette.generate(bit);
            int standard=mContext.getResources().getColor(R.color.colorPrimary);
            int vibrant = palette.getVibrantColor(standard);
            colore = vibrant;
            holder.barra.setBackgroundColor(vibrant);
            holder.coverArtista.setImageBitmap(bit);
        }
    }


    // Return the size of your dataset (invoked by the information manager)
    @Override
    public int getItemCount() {
        return setlists.size();
    }
}
