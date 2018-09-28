package com.example.cuiweicong.download;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public int getTheme() {
        return R.style.BaseDialogTheme;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            if (manager != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(this, tag);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }
}
