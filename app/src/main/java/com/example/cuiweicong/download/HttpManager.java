package com.example.cuiweicong.download;

import android.support.annotation.WorkerThread;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpManager {
    private OkHttpClient okHttpClient;
    private ProgressListener progressListener;

    private HttpManager() {
        progressListener = new DownloadProgressListener();
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

    }

    private static class SingletonHolder {
        static HttpManager instance = new HttpManager();
    }

    public static HttpManager getInstance() {
        return SingletonHolder.instance;
    }

    @WorkerThread
    public Response request(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response == null) {
                throw new IOException("response is null");
            }
            if (response.code() < 200 || response.code() > 300) {
                throw new Exception("http code is not 2XX , code is = " + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                System.out.println(responseBody.string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResponse(response);
        }
        return response;
    }

    private void closeResponse(Response response) {
        if (response == null) {
            return;
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            responseBody.close();
        }
    }

}
