package com.example.cuiweicong.download;

import android.content.Context;
import android.os.Environment;

public class FileUtils {

    /**
     * 通过url获取文件名称
     * @param url 链接
     * @return 文件名称
     */
    public static String getFileNameFromUrl(String url){
        if (url == null) {
            return null;
        }
        String[] strings = url.split("/");
        if (strings.length > 0) {
            return strings[strings.length - 1];
        }
        return url;
    }

    /**
     * 获取文件根路径
     */

    public static String getDiskFileRootPath() {
        String path = "";
        try {
            Context context = MyApplication.getContext();
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                path = Environment.getExternalStorageDirectory().getPath() + "/downloadX";
            } else {
                path = context.getCacheDir().getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

}
