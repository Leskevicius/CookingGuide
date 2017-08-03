package com.udacity.rokas.cookingguide.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.models.IngredientModel;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.utilities.TextUtils;

import java.util.List;

/**
 * Created by rokas on 8/1/17.
 */

public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private RecipeModel recipe;

    private List<RecipeModel> recipeList;

    public WidgetListProvider(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getBundleExtra(RecipeWidget.RECIPE_BUNDLE);
        this.recipeList = bundle.getParcelableArrayList(RecipeWidget.RECIPES);
        this.recipe = recipeList.get(0);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        this.recipe = recipeList.get(TerribleSolution.getCurrentRecipe());
    }

    @Override
    public void onDestroy() {
        // nothing to destroy
    }

    @Override
    public int getCount() {
        return recipe.getIngredientList() == null ? 0 : recipe.getIngredientList().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_ingredient_item);
        IngredientModel ingredient = recipe.getIngredientList().get(position);
        String ingredientName = ingredient.getIngredient();
        remoteViews.setTextViewText(R.id.recipe_widget_ingredient, ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1));
        remoteViews.setTextViewText(R.id.recipe_widget_amount, TextUtils.formatIngredientAmount(ingredient.getQuantity(), ingredient.getMeasure()));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
