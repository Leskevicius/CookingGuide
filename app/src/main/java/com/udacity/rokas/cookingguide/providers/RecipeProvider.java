package com.udacity.rokas.cookingguide.providers;

import android.os.AsyncTask;
import android.util.Log;

import com.udacity.rokas.cookingguide.models.JSONModelParser;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.utilities.NetworkUtils;
import com.udacity.rokas.cookingguide.utilities.URLBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created by rokas on 7/1/17.
 *
 * Provider class to grab recipes from the inter-tubes. Great abstraction.
 */

public class RecipeProvider {

    private static final String TAG = RecipeProvider.class.getCanonicalName();

    private static AsyncTask mBackgroundTask;
    private static RecipeProviderListener currentListener;
    // synchronized so we do not have two threads in this part of code. Would create a problem of finding who to return the results to.
    // there is a ton of improvement here, but I will not spend time on this as this will not be a huge project/reusability of this
    // provider.

    /**
     * Posts a request to get the recipes
     *
     * @param listener a {@link RecipeProviderListener} who to return the results to using a callback onComplete.
     */
    public static synchronized void requestRecipes(final RecipeProviderListener listener) {
        currentListener = listener;
        // start the job off of the main UI thread.
        mBackgroundTask = new AsyncTask() {

            private List<RecipeModel> recipes;

            @Override
            protected Object doInBackground(Object[] params) {
                String response = "";
                try {
                    response = NetworkUtils.getResponseFromHTTPUrl(URLBuilder.buildRecipeQuery());
                } catch (IOException e) {
                    Log.w(TAG, e.toString());
                }

                Log.w("rokas: ", response);

                recipes = JSONModelParser.parseRecipeModels(response);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                onComplete(recipes);
            }
        };

        mBackgroundTask.execute();
    }

    private static void onComplete(List<RecipeModel> recipes) {
        currentListener.onComplete(recipes);
    }

    public interface RecipeProviderListener {
        void onComplete(List<RecipeModel> recipes);
    }
}
