package com.hzsoft.musicdemo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MusicInfoActivity extends AppCompatActivity {

    private  String Path="文件的HTTP地址/ttt.mp3";
    private  String fileName = "ttt.mp3";
    private  static String  filePath= "/download/";
    private ProgressBar progressBar;
    private TextView textViewBack,textViewMusicName,textViewAuthorName, textViewCurrent;
    private Button button;
    private int FileLength;
    private int DownedFileLength=0;
    private InputStream inputStream;
    private URLConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);

        progressBar=(ProgressBar) findViewById(R.id.download_main_progressBarlist);
        textViewBack=(TextView) findViewById(R.id.download_main_back);
        textViewMusicName = findViewById(R.id.download_main_Music_name);
        textViewAuthorName = findViewById(R.id.download_main_Author_name);
        textViewCurrent  = findViewById(R.id.download_main_Text);
        button=(Button) findViewById(R.id.download_main_Button);
        button.setOnClickListener(new ButtonListener());
        
        initData();

        initListener();

    }

    private void initListener() {
        textViewBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        Path = intent.getStringExtra("Url");
        String musicName = intent.getStringExtra("MusicName");
        String authorName = intent.getStringExtra("AuthorName");
        fileName = authorName+"-"+musicName+".mp3";

        textViewMusicName.setText(musicName);
        textViewAuthorName.setText(authorName);

    }

    class ButtonListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            DownedFileLength=0;
            Thread thread=new Thread(){
                public void run(){
                    try {
                        DownFile(Path);
                    } catch (Exception e) {
                        Log.e("Error", "error");
                    }
                }
            };
            thread.start();
        }
    }

    private Handler handler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        progressBar.setMax(FileLength);
                        Log.i("文件长度----------->", progressBar.getMax()+"");
                        break;
                    case 1:
                        progressBar.setProgress(DownedFileLength);
                        int x=DownedFileLength*100/FileLength;
                        textViewCurrent.setText(x+"%");
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
            }
        }

    };

    private void DownFile(String urlString) {

        /*
         * 连接到服务器
         */

        try {
            URL url = new URL(urlString);
            connection = url.openConnection();
            if (connection.getReadTimeout() == 5) {
                Log.i("---------->", "当前网络有问题");
                // return;
            }
            inputStream = connection.getInputStream();

        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
         * 文件的保存路径和和文件名其中Nobody.mp3是在手机SD卡上要保存的路径，如果不存在则新建
         */
        String savePAth = Environment.getExternalStorageDirectory() + filePath;
        File file1 = new File(savePAth);
        if (!file1.exists()) {
            file1.mkdir();
        }
        String savePathString = Environment.getExternalStorageDirectory() + filePath + fileName;
        File file = new File(savePathString);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*
         * 向SD卡中写入文件,用Handle传递线程
         */
        Message message = new Message();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.setLength(FileLength);
            byte[] buf = new byte[1024 * 4];
            FileLength = connection.getContentLength();
            message.what = 0;
            handler.sendMessage(message);
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                randomAccessFile.write(buf, 0, length);
                DownedFileLength += length;
                Log.i("-------->", DownedFileLength + "");
                Message message1 = new Message();
                message1.what = 1;
                handler.sendMessage(message1);
            }
            inputStream.close();
            randomAccessFile.close();
            Message message2 = new Message();
            message2.what = 2;
            handler.sendMessage(message2);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}