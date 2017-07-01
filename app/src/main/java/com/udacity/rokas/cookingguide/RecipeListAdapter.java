package com.udacity.rokas.cookingguide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {


    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_item_title) public TextView mRecipeTitle;

        public RecipeListAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(itemView);
        }
    }

}
