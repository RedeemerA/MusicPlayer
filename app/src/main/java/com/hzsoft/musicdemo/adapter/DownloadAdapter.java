package com.hzsoft.musicdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzsoft.musicdemo.MusicInfoActivity;
import com.hzsoft.musicdemo.R;
import com.hzsoft.musicdemo.entity.SongModel;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends BaseAdapter {
    private List<SongModel> musicInfoList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;


    private List<String> demolist = new ArrayList<>();//你获取的网络数据
    private List<String> audiolist = new ArrayList<>();


    public DownloadAdapter (List<SongModel> musicInfoList, Context context){
        this.musicInfoList = musicInfoList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return musicInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view =  layoutInflater.inflate(R.layout.item_music, null);;

        TextView mTextViewMusicName = view.findViewById(R.id.tv_item_music_song_name),
                mTextViewAuthorName = view.findViewById(R.id.tv_item_music_author_name),
                mTextViewDownload = view.findViewById(R.id.tv_item_music_download);

        mTextViewMusicName.setText(musicInfoList.get(i).getName());
        mTextViewAuthorName.setText(musicInfoList.get(i).getSinger());



        mTextViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicInfoActivity.class);
                intent.putExtra("Url", musicInfoList.get(i).getUrl());
                intent.putExtra("MusicName", musicInfoList.get(i).getName());
                intent.putExtra("AuthorName", musicInfoList.get(i).getSinger());
                context.startActivity(intent);
            }
        });

        return view;
    }


}
