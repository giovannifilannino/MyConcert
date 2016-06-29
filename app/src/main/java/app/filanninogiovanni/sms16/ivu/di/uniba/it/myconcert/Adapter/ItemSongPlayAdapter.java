package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Song;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;

/**
 * Created by gianni on 29/06/16.
 */
public class ItemSongPlayAdapter extends ArrayAdapter<Song>{
    LayoutInflater inflater;
    List<Song> songs;
    private int layout;
    private Song song;
    private Context context;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder = null;

        if(convertView==null){
            convertView = inflater.inflate(layout,parent,false);

            viewholder = new ViewHolder();

            viewholder.titleSong = (TextView) convertView.findViewById(R.id.textSong);
            viewholder.playDeezer = (ImageButton) convertView.findViewById(R.id.playDeezer);
            viewholder.playYoutube = (ImageButton) convertView.findViewById(R.id.playYoutube);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        song = songs.get(position);

        if(song!=null) {
            viewholder.titleSong.setText(song.getTitle());

        }

        viewholder.playYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = songs.get(position).getTitle();
                String artist = songs.get(position).getArtist();
                Intent playYoutube = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/results?search_query="+ title + "+" + artist));
                context.startActivity(playYoutube);
            }
        });


        return convertView;
    }

    public ItemSongPlayAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(getContext());
        layout = resource;
        songs = objects;
        this.context = context;
    }

    private class ViewHolder{
        TextView titleSong;
        ImageButton playDeezer;
        ImageButton playYoutube;
    }
}
