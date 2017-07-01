package com.udacity.rokas.cookingguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.rokas.cookingguide.models.RecipeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {

    List<RecipeModel> recipes;

    public RecipeListAdapter() {
        recipes = new ArrayList<>();
    }

    public RecipeListAdapter(List<RecipeModel> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {
        String title = recipes.get(position).getName();
        holder.mRecipeTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        else return recipes.size();
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_item_title) public TextView mRecipeTitle;

        public RecipeListAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(itemView);
        }
    }

    public List<RecipeModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeModel> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
