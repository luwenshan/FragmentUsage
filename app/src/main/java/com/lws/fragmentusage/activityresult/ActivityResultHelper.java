package com.lws.fragmentusage.activityresult;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActivityResultHelper {
    private static final String TAG = "ActivityResult";
    private FragmentActivity mActivity;
    private RouterFragment mFragment;

    public ActivityResultHelper(FragmentActivity activity) {
        mActivity = activity;
        mFragment = getRouterFragment(mActivity);
    }

    public static ActivityResultHelper init(FragmentActivity activity) {
        return new ActivityResultHelper(activity);
    }

    public void startActivityForResult(Class<?> clazz, IActivityResultCallback callback) {
        Intent intent = new Intent(mActivity, clazz);
        startActivityForResult(intent, callback);
    }

    public void startActivityForResult(Intent intent, IActivityResultCallback callback) {
        mFragment.startActivityForResult(intent, callback);
    }

    private RouterFragment getRouterFragment(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        RouterFragment routerFragment = (RouterFragment) fm.findFragmentByTag(TAG);
        if (routerFragment == null) {
            routerFragment = RouterFragment.newInstance();
            fm.beginTransaction()
                    .add(routerFragment, TAG)
                    .commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        return routerFragment;
    }
}
