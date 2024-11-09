package com.cookandroid.test_ui.util;

import android.content.Context;
import android.util.Log;

public class LogMsgOutput {

    private static String lineOut() {
        int level = 4;
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        return ("at " + traces[level] + " ");
    }

    private static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuffer sb = new StringBuffer();
        sb.append(" [ ");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append(" :: ");
        sb.append(ste.getMethodName());
        sb.append(" ] ");
        sb.append(message);
        return sb.toString();
    }

    public static void logPrintOut(Context context, String logMsg) {
        //resources.getSystem().getString(R.string.execMode);
        if("debug".equals("debug") == true) {
            Log.d("MSG:::By-LogUtil:::", buildLogMsg(logMsg + " :: " + lineOut()));
        }
    }
}
