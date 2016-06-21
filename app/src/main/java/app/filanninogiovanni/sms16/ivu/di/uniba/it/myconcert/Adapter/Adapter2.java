package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by delmi on 09/06/2016.
 */
public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private String[] mDataset;
    LayoutInflater inflater;
    List<String> setlists;
    Setlist setList;
    private int layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
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
    public Adapter2(Context context, int resource, ArrayList<String> objects) {
        inflater = LayoutInflater.from(context);
        layout = resource;
        setlists = objects;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter2.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View convertView = inflater.inflate(layout,parent,false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(convertView);

        return vh;
    }


    public void addItem(int position, String data) {
        setlists.add(position, data);
        notifyItemInserted(position);
    }
    public void removeItem(int position) {
        setlists.remove(position);
        notifyItemRemoved(position);
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
