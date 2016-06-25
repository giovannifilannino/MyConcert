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

import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ResultFragment;

/**
 * Created by delmi on 09/06/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    LayoutInflater inflater;
    List<Setlist> setlists;
    OnItemClickListener mItemClickListener;
    Setlist setList;
    Context mContext;
    int colore;

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
       // CardView cardView;
        LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            nameArtist=(TextView) v.findViewById(R.id.artista);
           // nameVenue = (TextView) v.findViewById(R.id.textNameVenueItemList);
           // nameCity = (TextView) v.findViewById(R.id.textNameCityItemList);
            date = (TextView) v.findViewById(R.id.data);
            barra=(LinearLayout)v.findViewById(R.id.placeNameHolder);
            //cardView=(CardView)v.findViewById(R.id.cv);
            coverArtista = (ImageView) v.findViewById(R.id.placeImage);
            linearLayout=(LinearLayout) v.findViewById(R.id.mainHolder);
            linearLayout.setOnClickListener(this);

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
    public MyAdapter(Context context, int resource, List<Setlist> objects) {
        inflater = LayoutInflater.from(context);
        mContext=context;
        layout = resource;
        setlists = objects;
    }

    // Create new views (invoked by the information manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.coverArtista.setImageBitmap(setList.getCover());
       Palette.from(setList.getCover()).generate(new Palette.PaletteAsyncListener() {
           @Override
           public void onGenerated(Palette palette) {
               int bgColor = palette.getLightMutedColor(mContext.getResources().getColor(R.color.colorPrimary));
               colore=bgColor;
               holder.barra.setBackgroundColor(bgColor);
           }
       });
    }


    // Return the size of your dataset (invoked by the information manager)
    @Override
    public int getItemCount() {
        return setlists.size();
    }
}
