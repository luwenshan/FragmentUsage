package com.lws.fragmentusage.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.util.Random;

public class RouterFragment extends Fragment {
    private Random mCodeGenerator = new Random();
    private SparseArray<IActivityResultCallback> mCallbacks = new SparseArray<>();

    public RouterFragment() {
    }

    public static RouterFragment newInstance() {
        return new RouterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为 true，表示 configuration change 的时候，fragment 实例不会重新创建
        setRetainInstance(true);
    }

    public void startActivityForResult(Intent intent, IActivityResultCallback callback) {
        int requestCode = makeRequestCode();
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IActivityResultCallback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
    }

    /**
     * 随机生成唯一的requestCode，最多尝试10次
     */
    private int makeRequestCode() {
        int requestCode;
        int tryCount = 0;
        do {
            requestCode = mCodeGenerator.nextInt(0xFFFF);
            tryCount++;
        } while (tryCount < 10 && mCallbacks.indexOfKey(requestCode) >= 0);
        return requestCode;
    }
}
