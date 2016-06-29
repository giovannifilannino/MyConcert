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
 * Created by Giovanni on 06/06/2016.
 */
public class SetListAdapter extends ArrayAdapter<Setlist> {
    LayoutInflater inflater;
    List<Setlist> setlists;
    Setlist setList;
    private int layout;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder = null;

        if(convertView==null){
            convertView = inflater.inflate(layout,parent,false);

            viewholder = new ViewHolder();

            viewholder.nameArtist = (TextView) convertView.findViewById(R.id.textSong);
            viewholder.nameVenue = (TextView) convertView.findViewById(R.id.textNameVenueItemList);
            viewholder.nameCity = (TextView) convertView.findViewById(R.id.textNameCityItemList);
            viewholder.date = (TextView) convertView.findViewById(R.id.textDataItemList);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        setList = setlists.get(position);

        if(setList!=null) {
            viewholder.nameArtist.setText(setList.getArtistName());
            viewholder.nameVenue.setText(setList.getVenueName());
            viewholder.nameArtist.setText(setList.getCity());
            viewholder.date.setText(setList.getDate());
        }

        return convertView;
    }

    public SetListAdapter(Context context, int resource, List<Setlist> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(getContext());
        layout = resource;
        setlists = objects;
    }

    private class ViewHolder{
        TextView nameArtist;
        TextView nameVenue;
        TextView nameCity;
        TextView date;
    }

}
