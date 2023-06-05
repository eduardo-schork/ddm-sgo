package com.ddm.sgo.infra.local_storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private static SharedPreferences localStorageInstance;
    private static LocalStorage instance;

    public static LocalStorage getInstance(Context context) {
        if (instance == null) {
            instance = new LocalStorage();
            localStorageInstance = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }

        return instance;
    }

    public static String getValue(String key) {
        return localStorageInstance.getString(key, null);
    }

    public static void setValue(String key, String value) {
        final SharedPreferences.Editor editor = localStorageInstance.edit();
        editor.putString(key, value);
        editor.commit();
    }


}