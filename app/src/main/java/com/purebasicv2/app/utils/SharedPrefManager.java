package com.purebasicv2.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.purebasicv2.app.LoginActivity;
import com.purebasicv2.app.model.UserData;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "device_local01";
    private static final String KEY_MOBILE = "device_zone";
    private static final String KEY_USER_HASH = "device_hash";
    private static final String KEY_USER_LOGIN = "branding01";
    private static final String KEY_USER_ID = "kernel001";
    private static final String KEY_VERIFIED = "voka";
    private static final String SHARED_PREF_USER_DATA = "device_user_data";
    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLoginAdd(int id, String phone, String hash, boolean login) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, id);
        editor.putBoolean(KEY_USER_LOGIN, login);
        editor.putString(KEY_MOBILE, phone);
        editor.putString(KEY_USER_HASH, hash);
        editor.putBoolean(KEY_VERIFIED, false);
        editor.apply();
        return true;
    }

    public boolean updateVerify(boolean status) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_VERIFIED, status);
        return editor.commit();
    }

    public boolean getVerify() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_VERIFIED, false);
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(KEY_USER_ID, 0) != 0 && sharedPreferences.getBoolean(KEY_USER_LOGIN, false)) {
            return true;
        }
        return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
        return true;
    }

    public String getMobile() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE, null);
    }

    public String getUserHash() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HASH, null);
    }

    public String getUsernameHash() {
        final String username = SharedPrefManager.getInstance(mCtx).getMobile();
        final String hash = SharedPrefManager.getInstance(mCtx).getUserHash();
        return "?mobile=" + username + "&hash=" + hash;
    }


    public UserData getUserInfo() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_USER_DATA, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("userInfoJson", "");
        UserData userData = gson.fromJson(userJson, UserData.class);
        //userData.setRating(0.0);
        return userData;
    }

    public void setUserInfo(UserData user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString("userInfoJson", userJson);
        editor.commit();
    }

}