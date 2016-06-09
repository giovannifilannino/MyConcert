package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by delmi on 09/06/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    LayoutInflater inflater;
    List<Setlist> setlists;
    Setlist setList;
    private int layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nameArtist;
        TextView nameVenue;
        TextView nameCity;
        TextView date;
        public ViewHolder(View v) {
            super(v);
            nameArtist=(TextView) v.findViewById(R.id.textNameArtistItemList);
            nameVenue = (TextView) v.findViewById(R.id.textNameVenueItemList);
            nameCity = (TextView) v.findViewById(R.id.textNameCityItemList);
            date = (TextView) v.findViewById(R.id.textDataItemList);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, int resource, List<Setlist> objects) {
        inflater = LayoutInflater.from(context);
        layout = resource;
        setlists = objects;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View convertView = inflater.inflate(layout,parent,false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(convertView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        setList = setlists.get(position);
        holder.nameArtist.setText(setList.getArtistName());
        holder.nameVenue.setText(setList.getVenueName());
        holder.nameArtist.setText(setList.getCity());
        holder.date.setText(setList.getDate());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return setlists.size();
    }
}
