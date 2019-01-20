package com.lws.fragmentusage.permission;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PermissionFragment extends Fragment {
    private SparseArray<IPermissionListener> mCallbacks = new SparseArray<>();
    private Random mCodeGenerator = new Random();
    private FragmentActivity mActivity;

    public PermissionFragment() {
    }

    public static PermissionFragment newInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为 true，表示 configuration change 的时候，fragment 实例不会重新创建
        setRetainInstance(true);
        mActivity = getActivity();
    }

    public void requestPermissions(@NonNull String[] permissions, IPermissionListener callback) {
        int requestCode = makeRequestCode();
        mCallbacks.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionCallback(requestCode, permissions, grantResults);
    }

    private void handlePermissionCallback(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        IPermissionListener callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        if (callback == null) {
            return;
        }
        boolean allGranted = true;
        List<Permission> resultList = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            int grantResult = grantResults[i];
            Permission permission;
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                permission = new Permission(permissions[i], true);
            } else {
                boolean show = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[i]);
                permission = new Permission(permissions[i], false, show);
                allGranted = false;
            }
            resultList.add(permission);
        }
        callback.onAccepted(allGranted, resultList);
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
