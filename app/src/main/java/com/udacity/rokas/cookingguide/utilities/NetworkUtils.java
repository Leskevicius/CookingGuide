package com.udacity.rokas.cookingguide.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by rokas on 7/1/17.
 *
 * Network utilities to help with network related work.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();



    /**
     * An HTTP request. Used to make API calls
     *
     * @param url {@link URL} object representing GET request URL
     * @return {@link String} representation of response
     * @throws {@link IOException}
     */
    public static String getResponseFromHTTPUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();

            Scanner scanner = new Scanner(in);
            // Beginning of the string
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                Log.d(LOG_TAG, "Successful API call (method: getResponseFromHTTPUrl)");
                return scanner.next();
            } else {
                Log.d(LOG_TAG, "Unsuccessful API call (method: getResponseFromHTTPUrl");
                return null;
            }

        }
        finally {
            connection.disconnect();
        }
    }

    /**
     * Checks whether the device is connected/connecting to the internet
     *
     * @param context {@link Context} object from where this call is made.
     * @return true if connect to the internet, otherwise false;
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
