package com.cookandroid.test_ui.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManger {
    private static final String PREFS = "prefs";
    private static final int DEFAULT_VALUE_INT = -1;
    private static final String USER_ID = "userId";
    private static final String PROFILE_NAME = "profileName";
    private static final Boolean DEFAULT_VALUE_BOOLEAN = false;
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;
    private static UserManger instance;

    public static synchronized UserManger init(Context context) {
        if (instance == null) instance = new UserManger(context);
        return instance;
    }

    private UserManger(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public static synchronized UserManger getInstance(Context context) {
        if (instance == null) {
            instance = new UserManger(context);
        }
        return instance;
    }

    // ID 저장
    public static void setId(int value) {
        prefsEditor.putInt("ID", value).commit();
    }

    // ID 가져오기
    public static int getId() {
        return prefs.getInt("ID", DEFAULT_VALUE_INT);
    }

    // USER_ID 저장
    public static void setUserId(String value) {
        prefsEditor.putString(USER_ID, value).commit();
    }

    // USER_ID 가져오기
    public static String getUserId() {
        return prefs.getString(USER_ID, null);
    }

    // PROFILE_NAME 저장
    public static void setProfileName(String value) {
        prefsEditor.putString(PROFILE_NAME, value).commit();
    }

    // PROFILE_NAME 가져오기
    public static String getProfileName() {
        return prefs.getString(PROFILE_NAME, null);
    }

    // LEADER_YN 저장
    public static void setLeaderYn(Boolean value) {
        prefsEditor.putBoolean("LEADER_YN", value).commit();
    }

    // LEADER_YN 가져오기
    public static Boolean getLeaderYn() {
        return prefs.getBoolean("LEADER_YN", DEFAULT_VALUE_BOOLEAN);
    }
}
