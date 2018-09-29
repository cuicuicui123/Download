package com.example.cuiweicong.download.file;

import android.content.Context;
import android.os.Environment;

import com.example.cuiweicong.download.MyApplication;

import java.io.File;
import java.io.IOException;

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

    /**
     * 根据文件名创建文件
     * @param url 文件链接
     * @return 创建的文件
     * @throws IOException
     */
    public static File createFileByUrl(String url) throws IOException {
        String fileName = FileUtils.getFileNameFromUrl(url);
        File folder = new File(FileUtils.getDiskFileRootPath());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(String.format("%s/%s", FileUtils.getDiskFileRootPath(), fileName));
        file.createNewFile();
        return file;
    }

}
