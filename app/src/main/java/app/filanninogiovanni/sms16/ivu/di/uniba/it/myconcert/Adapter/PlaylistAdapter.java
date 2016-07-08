package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;


/**
 * Created by delmi on 08/07/2016.
 */

public class PlaylistAdapter extends ArrayAdapter<Setlist> {
    LayoutInflater inflater;
    Setlist setList;
    private int layout;
    List<Setlist> setlists;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder = null;

        if(convertView==null){
            convertView = inflater.inflate(layout,parent,false);

            viewholder = new ViewHolder();

            viewholder.nameArtist = (TextView) convertView.findViewById(R.id.artistaplaylist);
            viewholder.date = (TextView) convertView.findViewById(R.id.dataplaylist);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        setList = setlists.get(position);

        if(setList!=null) {
            viewholder.nameArtist.setText(setList.getArtistName());
            viewholder.date.setText(setList.getDate());
        }

        return convertView;
    }
    public PlaylistAdapter(Context context, int resource, List<Setlist> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(getContext());
        layout = resource;
        setlists = objects;
    }
    private class ViewHolder{
        TextView nameArtist;
        TextView date;
    }
}
