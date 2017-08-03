package com.udacity.rokas.cookingguide.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.udacity.rokas.cookingguide.R;
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

    private static final String ACTION_NEXT = "com.udacity.rokas.cookingguide.widget.action.NEXT_RECIPE";

    private static final String ACTION_PREVIOUS = "com.udacity.rokas.cookingguide.widget.action.PREVIOUS_RECIPE";

    private static final String ACTION_ON_BOOT = "com.udacity.rokas.cookingguide.widget.action.ON_BOOt";

    private List<RecipeModel> recipes;

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }


    public static Intent getActionNextRecipeIntent(Context context, int currentRecipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_NEXT);
        intent.putExtra(RecipeWidget.CURRENT_RECIPE, currentRecipe);
        return intent;
    }

    public static Intent getActionPreviousRecipeIntent(Context context, int currentRecipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_PREVIOUS);
        intent.putExtra(RecipeWidget.CURRENT_RECIPE, currentRecipe);
        return intent;
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
                final int currentRecipe = intent.getIntExtra(RecipeWidget.CURRENT_RECIPE, 0);
                handleActionNextRecipe(currentRecipe);
            } else if (ACTION_PREVIOUS.equals(action)) {
                final int currentRecipe = intent.getIntExtra(RecipeWidget.CURRENT_RECIPE, 0);
                handleActionPreviousRecipe(currentRecipe);
            } else if (ACTION_ON_BOOT.equals(action)) {
                RecipeProvider.requestRecipes(this);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNextRecipe(int currentRecipe) {
        TerribleSolution.setCurrentRecipe(currentRecipe < 3 ? ++currentRecipe : currentRecipe);
        handleActionTraversal();
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPreviousRecipe(int currentRecipe) {
        TerribleSolution.setCurrentRecipe(currentRecipe == 0 ? 0 : --currentRecipe);
        handleActionTraversal();
    }

    private void handleActionOnBoot() {
        if (recipes != null) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
            //Trigger data update to handle the GridView widgets and force a data refresh

            RecipeWidget.setRecipes(recipes);
            RecipeWidget.updateAllWidgets(this, appWidgetManager, appWidgetIds);
        }
    }

    private void handleActionTraversal() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
        //Trigger data update to handle the GridView widgets and force a data refresh

        RecipeWidget.updateAllWidgets(this, appWidgetManager, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_container);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_ingredients_list);
    }

    @Override
    public void onComplete(List<RecipeModel> recipes) {
        this.recipes = recipes;
        handleActionOnBoot();
    }
}
