package com.udacity.rokas.cookingguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.rokas.cookingguide.models.RecipeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {

    private List<RecipeModel> recipes;
    private RecipeOnClickListener listener;
    private Context context;

    public RecipeListAdapter(Context context, RecipeOnClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public RecipeListAdapter(Context context, RecipeOnClickListener listener, List<RecipeModel> recipes) {
        this(context, listener);
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
        String servings = "Servings: " + Integer.toString(recipes.get(position).getServings());
        String imageUrl = recipes.get(position).getImage();
        holder.mRecipeTitle.setText(title);
        holder.mRecipeDetail.setText(servings);
        if (TextUtils.isEmpty(imageUrl)) {
            holder.mImageView.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(imageUrl).into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        else return recipes.size();
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_item_title) public TextView mRecipeTitle;
        @BindView(R.id.recipe_item_detail) public TextView mRecipeDetail;
        @BindView(R.id.recipe_item_image) public ImageView mImageView;

        public RecipeListAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            RecipeModel recipe = recipes.get(getAdapterPosition());
            listener.onClick(recipe);
        }
    }

    public List<RecipeModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeModel> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public interface RecipeOnClickListener {
        void onClick(RecipeModel recipe);
    }
}
