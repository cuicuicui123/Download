package com.example.cuiweicong.download.download;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cuiweicong.download.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class DownloadListActivity extends AppCompatActivity {
    private LinearLayout ll;
    private List<View> itemViews;
    private Timer timer;
    private UpdateHandler updateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);

        ll = findViewById(R.id.ll);

        final DownloadManager downloadManager = DownloadManager.getInstance();
        List<DownloadRequest> list = downloadManager.getDownloadRequests();
        itemViews = new ArrayList<>();
        updateHandler = new UpdateHandler(this);

        Iterator<DownloadRequest> iterator = list.listIterator();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        while (iterator.hasNext()) {
            View view = layoutInflater.inflate(R.layout.download_item, ll, false);
            ll.addView(view);
            itemViews.add(view);
            final DownloadRequest downloadRequest = iterator.next();
            view.setTag(downloadRequest);
            view.findViewById(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (downloadRequest.isPause()) {
                        downloadRequest.restart();
                    } else {
                        downloadRequest.pause();
                    }
                }
            });
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateHandler.sendEmptyMessage(0);
            }
        }, 0, 200);
    }

    public void update(){
        for (View view : itemViews) {
            DownloadRequest downloadRequest = (DownloadRequest) view.getTag();
            if (downloadRequest != null) {
                TextView tv = view.findViewById(R.id.tv);
                DownloadProgressView progressView = view.findViewById(R.id.progress_view);
                progressView.setProgress(downloadRequest.isDone() ? 100 : downloadRequest.getDownloadPercent());
                if (downloadRequest.isDone()) {
                    tv.setText("已完成");
                    view.findViewById(R.id.tv_pause).setVisibility(View.GONE);
                } else {
                    tv.setText(String.format(Locale.CHINA, "%d%%", downloadRequest.getDownloadPercent()));
                }
            }
        }
    }

    static class UpdateHandler extends Handler{

        WeakReference<DownloadListActivity> weakReference;

        public UpdateHandler(DownloadListActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DownloadListActivity activity = weakReference.get();
            if (activity != null) {
                activity.update();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
