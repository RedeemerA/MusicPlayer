package com.hzsoft.musicdemo;

import static com.hzsoft.musicdemo.value.normalValue.SongFive;
import static com.hzsoft.musicdemo.value.normalValue.SongFour;
import static com.hzsoft.musicdemo.value.normalValue.SongOne;
import static com.hzsoft.musicdemo.value.normalValue.SongSeven;
import static com.hzsoft.musicdemo.value.normalValue.SongSix;
import static com.hzsoft.musicdemo.value.normalValue.SongThree;
import static com.hzsoft.musicdemo.value.normalValue.SongTwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hzsoft.musicdemo.adapter.DownloadAdapter;
import com.hzsoft.musicdemo.entity.SongModel;
import com.hzsoft.musicdemo.utils.PermissionCheckUtil;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private TextView mTextViewBack;
    private ListView mListViewMusic;

    private List<SongModel> songModelList;

    private DownloadAdapter downloadAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initView();

        initData();

        initListener();


    }

    private void initView() {

        mTextViewBack = findViewById(R.id.tv_music_back);

        mListViewMusic = findViewById(R.id.lv_music_list);

    }

    private void initListener() {

        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {


        songModelList = new ArrayList<>();
        SongModel musicInfo1 = new SongModel("Orca Team" , "Shane", SongOne);
        SongModel musicInfo2 = new SongModel("Cantina Rag", "Jackson F. Smith", SongTwo);
        SongModel musicInfo3 = new SongModel("Surface", "Robert John", SongThree);
        SongModel musicInfo4 = new SongModel("At The Restaurant", "Monolog Rockstars", SongFour);
        SongModel musicInfo5 = new SongModel("Humbug", "Crowander", SongFive);
        SongModel musicInfo6 = new SongModel("我的一个道姑朋友", "LON", SongSix);
        SongModel musicInfo7 = new SongModel("不问天", "萧忆情Alex", SongSeven);

        songModelList.add(musicInfo1);
        songModelList.add(musicInfo2);
        songModelList.add(musicInfo3);
        songModelList.add(musicInfo4);
        songModelList.add(musicInfo5);
        songModelList.add(musicInfo6);
        songModelList.add(musicInfo7);

        downloadAdapter = new DownloadAdapter(songModelList, DownloadActivity.this);
        mListViewMusic.setAdapter(downloadAdapter);

        PermissionCheckUtil.checkInternet(DownloadActivity.this);
        PermissionCheckUtil.checkLocation(DownloadActivity.this);
        PermissionCheckUtil.checkPermission(DownloadActivity.this);

    }

}