package com.udacity.rokas.cookingguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.providers.RecipeProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 *
 * This fragment represents a list of available recipes. It utilized a {@link android.support.v7.widget.RecyclerView}.
 */

public class RecipeListFragment extends Fragment implements RecipeProvider.RecipeProviderListener {

    public static String RECIPES = "recipes";

    private RecipeListAdapter recipeListAdapter;

    /**
     * Mandatory empty constructor for fragment manager.
     */

    public RecipeListFragment() {}

    /**
     * Method in order to retrieve a new instance of {@link RecipeListFragment}.
     *
     * @param bundle object to hold all information required by {@link RecipeListFragment}.
     * @return new instance of {@link RecipeListFragment}.
     */
    public static RecipeListFragment newInstance(Bundle bundle) {
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        recipeListFragment.setArguments(bundle);
        return recipeListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recipeRecyclerView = (RecyclerView) inflater.inflate(R.layout.recipe_list_fragment, container, false);
        Bundle args = getArguments();
        List<RecipeModel> recipeList = args.getParcelableArrayList(RECIPES);
        if (recipeList == null) {
            recipeListAdapter = new RecipeListAdapter();
            RecipeProvider.requestRecipes(this);

        } else {
            recipeListAdapter = new RecipeListAdapter(recipeList);
        }
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recipeRecyclerView.setAdapter(recipeListAdapter);

        return recipeRecyclerView;
    }

    @Override
    public void onComplete(List<RecipeModel> recipes) {
        recipeListAdapter.setRecipes(recipes);
    }
}
