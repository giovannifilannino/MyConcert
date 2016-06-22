package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.ErrorClass;

/**
 * Created by delmi on 09/06/2016.
 */
public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private String[] mDataset;
    LayoutInflater inflater;
    List<String> setlists;

    private static boolean  fatto=false;
    Setlist setList;
    private int layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        TextView song;
        ImageView image;
        LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            song=(TextView) v.findViewById(R.id.textSong);
            image=(ImageView)v.findViewById(R.id.immagineCestino);
            linearLayout=(LinearLayout) v.findViewById(R.id.mainHolder);

        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter2(Context context, int resource, ArrayList<String> objects) {
        inflater = LayoutInflater.from(context);
        layout = resource;
        setlists = objects;
        setlists.add(0,"Le tue canzoni");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter2.ViewHolder onCreateViewHolder(ViewGroup parent,
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


    public void addItem(int position, String data,Context context) {
        boolean err=false;
        for(int i =0;i<setlists.size();i++){
            if(setlists.get(i)==data){
                err=true;
                ErrorClass.onCreateDialog(ErrorClass.DIALOG_FAILADDSONG_ID,context);
            }
        }
        if(!err) {
            setlists.add(position, data);
            notifyItemInserted(position);
        }

    }
    public void removeItem(int position) {
        setlists.remove(position);
        notifyItemRemoved(position);
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
