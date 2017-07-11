package com.udacity.rokas.cookingguide.recipeDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.models.StepModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/3/17.
 */

public class RecipeDetailsStepsAdapter extends RecyclerView.Adapter<RecipeDetailsStepsAdapter.RecipeDetailsAdapterViewHolder>{

    private List<StepModel> steps;
    private RecipeStepOnClickListener listener;

    public RecipeDetailsStepsAdapter(RecipeStepOnClickListener listener) {
        this.listener = listener;
    }

    public RecipeDetailsStepsAdapter(RecipeStepOnClickListener listener, List<StepModel> steps) {
        this(listener);
        this.steps = steps;
    }

    @Override
    public RecipeDetailsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recipe_steps_item, parent, false);

        return new RecipeDetailsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailsAdapterViewHolder holder, int position) {
        String shortDescription = steps.get(position).getShortDescription();
        holder.recipeStepDetails.setText(shortDescription);

        if (position == 0) {
            holder.divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        else return steps.size();
    }

    public class RecipeDetailsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_details_steps_description) public TextView recipeStepDetails;
        @BindView(R.id.recipe_details_steps_divider) public View divider;

        public RecipeDetailsAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(steps.get(getAdapterPosition()));
        }
    }

    public interface RecipeStepOnClickListener {
        void onClick(StepModel step);
    }

}
