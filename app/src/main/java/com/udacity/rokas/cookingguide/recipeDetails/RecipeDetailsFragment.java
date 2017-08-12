package com.udacity.rokas.cookingguide.recipeDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.RecipeActivity;
import com.udacity.rokas.cookingguide.RecipeListActivity;
import com.udacity.rokas.cookingguide.RecipeListFragment;
import com.udacity.rokas.cookingguide.models.IngredientModel;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.models.StepModel;
import com.udacity.rokas.cookingguide.recipeStep.RecipeStepFragment;
import com.udacity.rokas.cookingguide.utilities.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 */

public class RecipeDetailsFragment extends Fragment {

    public static final String TAG = RecipeDetailsFragment.class.getCanonicalName();

    public static final String STEP = "step";

    @BindView(R.id.recipe_details_ingredients)
    TextView ingredientsView;

    @BindView(R.id.recipe_details_steps_list)
    LinearLayout stepsList;

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
            ingredientsView.setFocusableInTouchMode(true);

            // set up app bar
            setAppBarTitle(recipe.getName());

            // set up the recycler view
            for (int i = 0; i < recipe.getStepList().size(); i++) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View stepItem = layoutInflater.inflate(R.layout.recipe_steps_item, container, false);
                TextView stepDescriptionView = (TextView) stepItem.findViewById(R.id.recipe_details_steps_description);
                stepDescriptionView.setText(recipe.getStepList().get(i).getShortDescription());
                if (i == 0) {
                    View horizontalRule = stepItem.findViewById(R.id.recipe_details_steps_divider);
                    horizontalRule.setVisibility(View.GONE);
                }

                final int position = i;
                stepItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onStepClick(recipe.getStepList().get(position));
                    }
                });

                stepsList.addView(stepItem);
            }
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
            ((RecipeListActivity) getActivity()).setUpAppBarBackButton(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setAppBarTitle(recipe.getName());
        }
    }

    public void onStepClick(StepModel step) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailsFragment.STEP, step);
        bundle.putParcelable(RecipeListFragment.RECIPE, recipe);
        RecipeStepFragment fragment = RecipeStepFragment.newInstance(bundle);
        if (getActivity() instanceof RecipeActivity) {
            ((RecipeActivity) getActivity()).addFragment(fragment, R.id.recipe_step_fragment_container,
                RecipeStepFragment.TAG);

        }

    }
}
