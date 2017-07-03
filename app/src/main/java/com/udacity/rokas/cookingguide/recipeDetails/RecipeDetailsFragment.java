package com.udacity.rokas.cookingguide.recipeDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.RecipeListActivity;
import com.udacity.rokas.cookingguide.RecipeListFragment;
import com.udacity.rokas.cookingguide.models.IngredientModel;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.models.StepModel;
import com.udacity.rokas.cookingguide.utilities.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsStepsAdapter.RecipeStepOnClickListener{

    @BindView(R.id.recipe_details_ingredients) TextView ingredientsView;
    @BindView(R.id.recipe_details_steps_recycler_view) RecyclerView stepsRecyclerView;

    private RecipeModel recipe;

    public static RecipeDetailsFragment newInstance(Bundle bundle) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);
        return recipeDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_details_fragment, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RecipeListFragment.RECIPE);
        } else {
            Bundle bundle = getArguments();
            if (bundle != null) {
                recipe = bundle.getParcelable(RecipeListFragment.RECIPE);
            }
        }

        if (recipe != null) {
            StringBuilder sb = new StringBuilder();
            List<IngredientModel> ingredients = recipe.getIngredientList();
                sb.append(TextUtils.ingredientListToString(ingredients));

            ingredientsView.setText(sb.toString());

            // set up app bar
            setAppBarTitle(recipe.getName());

            // set up the recycler view
            RecipeDetailsStepsAdapter adapter = new RecipeDetailsStepsAdapter(this, recipe.getStepList());
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            stepsRecyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipe != null) {
            outState.putParcelable(RecipeListFragment.RECIPE, recipe);
        }
    }

    private void setAppBarTitle(String title) {
        if (getActivity() instanceof RecipeListActivity) {
            ((RecipeListActivity) getActivity()).setAppBarTitle(title);
        }
    }

    @Override
    public void onClick(StepModel step) {
        // step clicked!
    }
}
