package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.app.Activity;
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
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.StringTokenizer;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Song;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.R;
import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Utility.DeezerPlayTrack;

/**
 * Created by gianni on 29/06/16.
 */
public class ItemSongPlayAdapter extends ArrayAdapter<Song>{
    LayoutInflater inflater;
    List<Song> songs;
    private int layout;
    private Song song;
    private Context context;
    private DeezerPlayTrack deezerPlayTrack;
    private static boolean playSong = false;

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

        viewholder.playDeezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = songs.get(position).getTitle();
                String queryTitle = "";

                ImageButton imageButton = (ImageButton) v;

                try {
                    queryTitle = URLEncoder.encode(title, Charset.defaultCharset().name());

                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!playSong) {
                    imageButton.setImageResource(android.R.drawable.ic_media_pause);
                    playSong = true;
                    deezerPlayTrack.PlaySong(queryTitle);
                } else {
                    imageButton.setImageResource(android.R.drawable.ic_media_play);
                    playSong = false;
                    deezerPlayTrack.StopSong();

                }
                }
        });


        return convertView;
    }

    public ItemSongPlayAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        deezerPlayTrack = DeezerPlayTrack.getIstance((Activity) context);
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
