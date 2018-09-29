package com.example.cuiweicong.download.download;

import com.example.cuiweicong.download.http.HttpManager;

import okhttp3.Call;

public class DownloadRequest implements Runnable {
    private Builder builder;
    private Call call;
    private long contentLength;
    private volatile boolean done;
    private volatile long downLength;
    private volatile boolean pause = false;

    public DownloadRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void run() {
        HttpManager.getInstance().request(builder.url, this);
    }

    public synchronized void cancel() {
        call.cancel();
    }

    public synchronized void pause(){
        pause = true;
    }

    public synchronized void restart(){
        pause = false;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public int getDownloadPercent() {
        if (contentLength == 0) {
            return 0;
        }
        return (int) (downLength * 100 / contentLength);
    }

    public String getUrl() {
        return builder.url;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public long getDownLength() {
        return downLength;
    }

    public void setDownLength(long downLength) {
        this.downLength = downLength;
    }

    public boolean isPause() {
        return pause;
    }

    public static class Builder {
        private String url;

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public DownloadRequest build() {
            return new DownloadRequest(this);
        }
    }

}
