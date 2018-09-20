package com.example.cuiweicong.download;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class MainThreadUtils {
    private static final int MESSAGE_TOAST = 1001;
    private static final int MESSAGE_TOAST_LONG = 1002;
    private static Handler handler;

    private static final Object handlerLock = new Object();

    public static Handler getHandler() {
        if (handler == null) {
            synchronized (handlerLock) {
                if (handler == null) {
                    initHandler();
                }
            }
        }
        return handler;
    }

    private static void initHandler() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_TOAST:
                        Toast.makeText(MyApplication.getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case MESSAGE_TOAST_LONG:
                        Toast.makeText(MyApplication.getContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public static void toast(String msg){
        if (msg != null && !msg.isEmpty()) {
            Message message = getHandler().obtainMessage(MESSAGE_TOAST, msg);
            message.sendToTarget();
        }
    }

}
