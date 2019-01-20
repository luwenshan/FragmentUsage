package com.lws.fragmentusage.permission;

import java.util.List;

public interface IPermissionListener {
    void onAccepted(boolean isAllGranted, List<Permission> permissions);
}
