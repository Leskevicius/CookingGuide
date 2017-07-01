package com.udacity.rokas.cookingguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rokas.cookingguide.models.RecipeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 *
 * This fragment represents a list of available recipes. It utilized a {@link android.support.v7.widget.RecyclerView}.
 */

public class RecipeListFragment extends Fragment {

    public static String RECIPES = "recipes";

    @BindView(R.id.recipe_recycler_view) private RecyclerView recipeRecyclerView;

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
    public RecipeListFragment newInstance(Bundle bundle) {
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        recipeListFragment.setArguments(bundle);
        return recipeListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());

        Bundle args = getArguments();
        List<RecipeModel> recipeList = args.getParcelableArrayList(RECIPES);
        recipeListAdapter = new RecipeListAdapter(recipeList);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recipeRecyclerView.setAdapter(recipeListAdapter);

        return recipeRecyclerView;
    }
}
