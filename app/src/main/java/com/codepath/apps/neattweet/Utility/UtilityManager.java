package com.codepath.apps.neattweet.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by vidhurvoora on 7/31/16.
 */
public class UtilityManager {

    private static UtilityManager sInstance;

    public static synchronized UtilityManager getSharedInstance() {
        if ( sInstance == null ) {
            sInstance = new UtilityManager();
        }
        return sInstance;
    }

    public Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    public boolean shouldPerformNetworkRequest(Context context) {
        return isNetworkAvailable(context) && isOnline();
    }
}
