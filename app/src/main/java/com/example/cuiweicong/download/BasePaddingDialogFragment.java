package com.example.cuiweicong.download;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public abstract class BasePaddingDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.BaseDialogTheme);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER); //可设置dialog的位置
            window.getDecorView().setPadding(getPadding(), 0, getPadding(), 0); //消除边距
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        return dialog;
    }

    protected int getPadding(){
        return 0;
    }

}
