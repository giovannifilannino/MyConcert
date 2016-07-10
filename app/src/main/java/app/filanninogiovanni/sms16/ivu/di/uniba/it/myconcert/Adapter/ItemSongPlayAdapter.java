package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
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


    private boolean[] checked;
    private boolean[] visibles;
    public boolean visible;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewholder = null;

        if(convertView==null){
            convertView = inflater.inflate(layout,parent,false);

            viewholder = new ViewHolder();

            viewholder.titleSong = (TextView) convertView.findViewById(R.id.textSong);
            viewholder.playDeezer = (ImageButton) convertView.findViewById(R.id.playDeezer);
            viewholder.playYoutube = (ImageButton) convertView.findViewById(R.id.playYoutube);
            viewholder.sendData = (CheckBox) convertView.findViewById(R.id.sendThisSong);
            viewholder.sendData.setFocusable(false);
            viewholder.sendData.setFocusableInTouchMode(false);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        if(visible){
            viewholder.sendData.setVisibility(View.VISIBLE);
        }else {
            viewholder.sendData.setVisibility(View.GONE);
        }
        song = songs.get(position);


        if(song!=null) {
            viewholder.titleSong.setText(song.getTitle());

        }


        viewholder.sendData.setChecked(checked[position]);
        viewholder.sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("machecazzodiobaliss","" + position);
                if(((CheckBox) v).isChecked()){
                    checked[position] = true;
                } else {
                    checked[position] = false;
                }
            }
        });





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
                String queryTitle = title.replaceAll("\\s+","%20");
                String artista = songs.get(position).getArtist();
                ImageButton imageButton = (ImageButton) v;

                if(!playSong) {
                    imageButton.setImageResource(R.drawable.ic_stop_black_24dp);
                    playSong = true;
                    deezerPlayTrack.PlaySong(queryTitle,artista);
                } else {
                    imageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    playSong = false;
                    deezerPlayTrack.StopSong();

                }
                }
        });


        return convertView;
    }

    public void setVisible(){
        visible=true;
        notifyDataSetChanged();

    }
    public void setGone(){
        visible=false;
        notifyDataSetChanged();

    }
    public String[] getSelected(){
        String[] songchecked=new String[getItemCount()];

        for(int i=0;i<getItemCount();i++){
            if(checked[i]){
                songchecked[i]=songs.get(i).getTitle();
            }
        }
        return songchecked;
    }




    public ItemSongPlayAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        deezerPlayTrack = DeezerPlayTrack.getIstance((Activity) context);
        inflater = LayoutInflater.from(getContext());
        layout = resource;
        songs = objects;
        this.context = context;
        checked = new boolean[songs.size()];
        visibles=new boolean[songs.size()];
    }

    private class ViewHolder{
        TextView titleSong;
        ImageButton playDeezer;
        ImageButton playYoutube;
        CheckBox sendData;
    }

    public int getItemCount() {
        return songs.size();
    }



}
