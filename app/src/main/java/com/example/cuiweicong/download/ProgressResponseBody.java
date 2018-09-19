package com.example.cuiweicong.download;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;
    private ResponseBody responseBody;
    private DownloadRequest downloadRequest;

    public ProgressResponseBody(Response response, ProgressListener progressListener) {
        this.progressListener = progressListener;
        if (response != null) {
            responseBody = response.body();
            Request request = response.request();
            String url = request.url().toString();
            this.downloadRequest = DownloadManager.getInstance().findDownloadRequestByUrl(url);
        }
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1,
                        downloadRequest);
                return bytesRead;
            }
        };
    }

}
