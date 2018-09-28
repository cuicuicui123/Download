package com.example.cuiweicong.download;

import android.support.annotation.WorkerThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpManager {
    private OkHttpClient okHttpClient;

    private HttpManager() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    private static class SingletonHolder {
        static HttpManager instance = new HttpManager();
    }

    public static HttpManager getInstance() {
        return SingletonHolder.instance;
    }

    @WorkerThread
    public Response request(String url, DownloadRequest downloadRequest) {
        Response response = null;
        FileOutputStream fos = null;
        try {
            String fileName = FileUtils.getFileNameFromUrl(url);
            File folder = new File(FileUtils.getDiskFileRootPath());
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(String.format("%s/%s", FileUtils.getDiskFileRootPath(), fileName));
            file.createNewFile();
            fos = new FileOutputStream(file);
            Request request = new Request.Builder()
                    .addHeader("Range", String.format(Locale.CHINA, "bytes=%d-", downloadRequest.getDownLength()))
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);
            downloadRequest.setCall(call);
            response = call.execute();
            if (response == null) {
                throw new IOException("response is null");
            }
            if (response.code() < 200 || response.code() > 300) {
                throw new Exception("http code is not 2XX , code is = " + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                downloadRequest.setContentLength(responseBody.contentLength());
                InputStream inputStream = responseBody.byteStream();
                byte[] buff = new byte[2048];
                int len;
                boolean done = false;
                while (!done) {
                    if (!downloadRequest.isPause()) {
                        if ((len = inputStream.read(buff)) != -1) {
                            fos.write(buff, 0, len);
                            downloadRequest.setDownLength(downloadRequest.getDownLength() + len);
                        } else {
                            done = true;
                        }
                    }
                }
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Utils.closeQuietly(response);
            Utils.closeQuietly(fos);
        }
        return response;
    }
}
