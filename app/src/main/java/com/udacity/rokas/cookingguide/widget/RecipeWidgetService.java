package com.udacity.rokas.cookingguide.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.RecipeListAdapter;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.providers.RecipeProvider;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecipeWidgetService extends IntentService implements RecipeProvider.RecipeProviderListener {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_NEXT = "com.udacity.rokas.cookingguide.widget.action.NEXT_RECIPE";

    private static final String ACTION_PREVIOUS = "com.udacity.rokas.cookingguide.widget.action.PREVIOUS_RECIPE";

    private static final String ACTION_ON_BOOT = "com.udacity.rokas.cookingguide.widget.action.ON_BOOt";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.udacity.rokas.cookingguide.widget.extra.PARAM1";

    private static final String EXTRA_PARAM2 = "com.udacity.rokas.cookingguide.widget.extra.PARAM2";

    private static List<RecipeModel> recipes;

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionNextRecipe(Context context, String param1, String param2) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_NEXT);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionPreviousRecipe(Context context, String param1, String param2) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_PREVIOUS);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionOnBoot(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_ON_BOOT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEXT.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionNextRecipe(param1, param2);
            } else if (ACTION_PREVIOUS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionPreviousrecipe(param1, param2);
            } else if (ACTION_ON_BOOT.equals(action)) {
                RecipeProvider.requestRecipes(this);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNextRecipe(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPreviousrecipe(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionOnBoot() {
        if (recipes != null && recipes.get(0) != null) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
            //Trigger data update to handle the GridView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_container);

            RecipeWidget.updateAllWidgets(this, appWidgetManager, appWidgetIds, recipes.get(0));
        }
    }

    @Override
    public void onComplete(List<RecipeModel> recipes) {
        this.recipes = recipes;
        handleActionOnBoot();
    }
}
