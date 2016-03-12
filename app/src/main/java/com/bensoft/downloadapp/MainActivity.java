package com.bensoft.downloadapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bensoft.downloadapp.entities.FileInfor;
import com.bensoft.downloadapp.services.DownloadService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView mTVFileName;
    Button mBtnStart;
    Button mBtnStop;
    ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content_layout);
        mTVFileName = (TextView)findViewById(R.id.fileName);
        mBtnStart = (Button)findViewById(R.id.start);
        mBtnStop = (Button) findViewById(R.id.pause);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        final FileInfor fileInfor = new FileInfor(1,"http://ww4.sinaimg.cn/square/59b3f4eejw1f1ugxdscpvj20qo0zk45p.jpg","weiqi.jpg",0,0);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadService.startFileDownloadAction(MainActivity.this,fileInfor,TAG);
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadService.stopFileDownloadAction(MainActivity.this,fileInfor,TAG);
            }
        });
    }



}
