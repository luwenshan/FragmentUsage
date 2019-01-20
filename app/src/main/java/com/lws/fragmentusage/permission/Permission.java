package com.lws.fragmentusage.permission;

public class Permission {
    public final String name;
    public final boolean granted;
    /**
     * false 选择了 Don’t ask again
     */
    public final boolean shouldShowRequestPermissionRational;

    public Permission(String name, boolean granted) {
        this(name, granted, false);
    }

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRational) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRational = shouldShowRequestPermissionRational;
    }
}
