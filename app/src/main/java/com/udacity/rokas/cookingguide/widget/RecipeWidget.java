package com.udacity.rokas.cookingguide.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.RecipeListActivity;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.utilities.TextUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
        int appWidgetId, RecipeModel recipe) {

        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.recipe_widget_ingredients, TextUtils.ingredientListToString(recipe.getIngredientList()));

        views.setOnClickPendingIntent(R.id.recipe_widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeWidgetService.startActionOnBoot(context);
    }

    public static void updateAllWidgets(Context context, AppWidgetManager widgetManager, int appWidgetIds[], RecipeModel recipe) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context,widgetManager, appWidgetIds[i], recipe);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

