package com.lws.fragmentusage.activityresult;

import android.content.Intent;
import android.support.annotation.Nullable;

public interface IActivityResultCallback {
    void onActivityResult(int resultCode, @Nullable Intent data);
}
