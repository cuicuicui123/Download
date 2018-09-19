package com.example.cuiweicong.download;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Button buttonLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn);
        button2 = findViewById(R.id.btn2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logI("start");
                DownloadManager downloadManager = DownloadManager.getInstance();
                downloadManager.downLoad(FileUrl.picList.get(0));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logI("start2");
                DownloadManager downloadManager = DownloadManager.getInstance();
                downloadManager.downLoad(FileUrl.picList.get(1));
            }
        });
        buttonLook = findViewById(R.id.btn_look);
        buttonLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DownloadListActivity.class);
                startActivity(intent);
            }
        });

    }
}
