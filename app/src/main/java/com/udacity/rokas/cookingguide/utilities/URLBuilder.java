package com.udacity.rokas.cookingguide.utilities;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rokas on 7/1/17.
 */

public class URLBuilder {
    private static final String TAG = URLBuilder.class.getCanonicalName();

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildRecipeQuery() {

        URL url = null;
        try {
            url = new URL(RECIPE_URL);
        } catch (MalformedURLException e) {
            Log.w(TAG, e.toString());
        }
        return url;
    }

}
