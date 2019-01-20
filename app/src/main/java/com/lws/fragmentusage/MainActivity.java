package com.lws.fragmentusage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lws.fragmentusage.activityresult.ActivityResultHelper;
import com.lws.fragmentusage.activityresult.IActivityResultCallback;
import com.lws.fragmentusage.permission.IPermissionListener;
import com.lws.fragmentusage.permission.Permission;
import com.lws.fragmentusage.permission.PermissionHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void requestPermission(View view) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        PermissionHelper.init(this).requestPermissions(permissions, new IPermissionListener() {
            @Override
            public void onAccepted(boolean isAllGranted, List<Permission> permissions) {
                if (isAllGranted) {
                    Toast.makeText(MainActivity.this, "全部申请成功！", Toast.LENGTH_SHORT).show();
                } else {
                    for (Permission permission : permissions) {
                        if (!permission.granted && permission.shouldShowRequestPermissionRational) {
                            PermissionHelper.requestDialogAgain(MainActivity.this, "哈哈哈哈", "需要权限啊！", "好", "不好");
                        }
                    }
                }
            }
        });
    }

    public void activityResult(View view) {
        ActivityResultHelper.init(this).startActivityForResult(TestActivity.class, new IActivityResultCallback() {
            @Override
            public void onActivityResult(int resultCode, @Nullable Intent data) {
                if (resultCode == RESULT_OK && data != null) {
                    int value = data.getIntExtra(TestActivity.ARG_NAME, 0);
                    Toast.makeText(MainActivity.this, "返回值是：" + value, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
