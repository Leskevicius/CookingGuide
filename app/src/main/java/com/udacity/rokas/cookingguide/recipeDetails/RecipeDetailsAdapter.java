package com.udacity.rokas.cookingguide.recipeDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.models.StepModel;

import java.util.ArrayList;

/**
 * Created by rokas on 7/18/17.
 */

public class RecipeDetailsAdapter extends ArrayAdapter<StepModel> {

    private ArrayList<StepModel> steps;

    Context context;

    private RecipeStepOnClickListener listener;

    public RecipeDetailsAdapter(ArrayList<StepModel> steps, Context context, RecipeStepOnClickListener listener) {
        super(context, R.layout.recipe_steps_item, steps);
        this.steps = steps;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StepModel step = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_steps_item, parent, false);
        TextView stepDescription = (TextView) view.findViewById(R.id.recipe_details_steps_description);
        if (step != null) {
            stepDescription.setText(step.getShortDescription());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(steps.get(position));
            }
        });
        return view;
    }

    public interface RecipeStepOnClickListener {

        void onClick(StepModel step);
    }
}
