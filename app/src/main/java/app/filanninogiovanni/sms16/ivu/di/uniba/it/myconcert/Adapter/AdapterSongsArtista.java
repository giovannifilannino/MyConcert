package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by hp1 on 28-12-2014.
 */
public class AdapterSongsArtista extends RecyclerView.Adapter<AdapterSongsArtista.ViewHolder> {


    private int mIcons[];
    private ArrayList<String> songs;
    Context context;
    OnItemClickListener mItemClickListener;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView song;
        ImageView imageView;
        Context contxt;


        public ViewHolder(View itemView, int ViewType, Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            song = (TextView) itemView.findViewById(R.id.scelta);
            imageView = (ImageView) itemView.findViewById(R.id.rowIcon);


        }


        @Override
        public void onClick(View v) {
            //if(!isPositionHeader(getPosition())) {
            //  if (mItemClickListener != null) {

            //mItemClickListener.onItemClick(itemView, getPosition()-1, mNavTitles[getPosition() - 1]);
            //}
            //}
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String scelta);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public AdapterSongsArtista(ArrayList<String> songs, int[] idImg, Context passedContext) {
        this.songs = songs;
        this.mIcons = idImg;
        this.context = passedContext;


    }


    @Override
    public AdapterSongsArtista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scelte_drawer, parent, false);

        ViewHolder vhHeader = new ViewHolder(v, viewType, context);

        return vhHeader;

    }


    @Override
    public void onBindViewHolder(AdapterSongsArtista.ViewHolder holder, int position) {

        holder.song.setText(songs.get(position));
        holder.imageView.setImageResource(mIcons[position]);


    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return songs.size();
    }


}