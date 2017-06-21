package com.example.igork.palaverapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;



import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;


import helper.NetworkHelper;

/**
 * Created by stefa on 22.02.2016.
 */
public class PalaverApplication extends Application {

    private SharedPreferences prefs;
    public int notificationId = 1;

    public void setContext(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("palaverapp", MODE_PRIVATE);
    }

    private Context context;

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public String getUsername() {
        return prefs.getString("username", null);
    }

    public void setUsername(String username) {
        SharedPreferences.Editor e = prefs.edit();
        e.putString("username", username);
        e.commit();
    }

    public String getPassword() {
        return prefs.getString("password", null);
    }

    public void setPassword(String password) {
        SharedPreferences.Editor e = prefs.edit();
        e.putString("password", password);
        e.commit();
    }

    public void sendTokenToServer(String token) {
        new NetworkHelper(null).execute(NetworkHelper.ApiCommand.USER_PUSHTOKEN.toString(), "{\"Username\":\"" + getUsername() + "\",\"Password\":\"" + getPassword() + "\",\"PushToken\":\"" + token + "\"}");
    }


}
