package com.example.cuiweicong.download;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cuiweicong.download.download.DownloadListActivity;
import com.example.cuiweicong.download.download.DownloadManager;
import com.example.cuiweicong.download.file.FileUrl;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Button buttonLook;
    private int requestCode = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            initContentView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PermissionChecker.checkSelfPermission(this, Manifest
                .permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else {
            initContentView();
        }
    }

    private void initContentView() {
        button = findViewById(R.id.btn);
        button2 = findViewById(R.id.btn2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logI("start");
                DownloadManager downloadManager = DownloadManager.getInstance();
                downloadManager.downLoad(FileUrl.picList.get(0), MainActivity.this);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logI("start2");
                DownloadManager downloadManager = DownloadManager.getInstance();
                downloadManager.downLoad(FileUrl.picList.get(1), MainActivity.this);
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
