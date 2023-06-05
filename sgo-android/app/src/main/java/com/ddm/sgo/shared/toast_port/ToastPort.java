package com.ddm.sgo.shared.toast_port;

import android.content.Context;
import android.widget.Toast;

public class ToastPort {
    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
