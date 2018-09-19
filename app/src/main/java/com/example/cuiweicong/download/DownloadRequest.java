package com.example.cuiweicong.download;

public class DownloadRequest implements Runnable {
    private Builder builder;

    public DownloadRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void run() {
        HttpManager.getInstance().request(builder.url);
        DownloadManager.getInstance().removeDownloadRequest(this);
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
