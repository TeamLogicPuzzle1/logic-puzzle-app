package com.cookandroid.test_ui.util;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManger {
    private static final String PREFS = "prefs";
    private static final String Access_Token = "Access_Token";
    private static final String Refresh_Token = "Refresh_Token";
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;
    private static TokenManger instance;

    public static synchronized TokenManger init(Context context) {
        if (instance == null) instance = new TokenManger(context);
        return instance;
    }

    private TokenManger(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    // Singleton method to get the instance of TokenManger
    public static synchronized TokenManger getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManger(context);
        }
        return instance;
    }

    // Access Token 저장
    public static void setAccessToken(String value) {
        prefsEditor.putString(Access_Token, value).commit();
    }

    // Access Token 가져오기
    public static String getAccessToken() {
        return prefs.getString(Access_Token, null);
    }

    // Refresh Token 저장
    public static void setRefreshToken(String value) {
        prefsEditor.putString(Refresh_Token, value).commit();
    }

    // Refresh Token 가져오기
    public static String getRefreshToken() {
        return prefs.getString(Refresh_Token, null);
    }

    // 모든 토큰 삭제
    public static void clearToken() {
        prefsEditor.clear().apply();
    }
}
