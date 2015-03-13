package com.wordpress.smdaudhilbe.utils;

import android.os.Build;
import android.view.Window;

/**
 * Created by mohammed-2284 on 12/03/15.
 */
public class WindowsCompatUtils {

    //  setting status bar color
    public static void setStatusBarColor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }
}