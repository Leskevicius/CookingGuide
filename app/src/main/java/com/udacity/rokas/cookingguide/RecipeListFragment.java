package com.udacity.rokas.cookingguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.providers.RecipeProvider;
import com.udacity.rokas.cookingguide.recipeDetails.RecipeDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/1/17.
 *
 * This fragment represents a list of available recipes. It utilized a {@link android.support.v7.widget.RecyclerView}.
 */

public class RecipeListFragment extends Fragment implements RecipeProvider.RecipeProviderListener, RecipeListAdapter.RecipeOnClickListener {

    @BindView(R.id.recipe_recycler_view) RecyclerView recipeRecyclerView;
    @BindView(R.id.recipe_list_loading) ProgressBar progressBar;


    public static final String RECIPES = "recipes";
    public static final String RECIPE = "recipe";

    private RecipeListAdapter recipeListAdapter;
    private List<RecipeModel> recipeList;

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
        View view = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        ButterKnife.bind(this, view);
        Bundle args = getArguments();
        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(RECIPES);
        } else {
            recipeList = args.getParcelableArrayList(RECIPES);
        }
        if (recipeList == null) {
            showProgressBar();
            recipeListAdapter = new RecipeListAdapter(this);
            RecipeProvider.requestRecipes(this);

        } else {
            recipeListAdapter = new RecipeListAdapter(this, recipeList);
        }
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recipeRecyclerView.setAdapter(recipeListAdapter);

        // set up appbar
        setAppBarTitle(getString(R.string.recipe_list_app_bar_title));


        return view;
    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            onClickEnabled = true;
//            setAppBarTitle(getString(R.string.recipe_list_app_bar_title));
//        }
//    }

    @Override
    public void onComplete(List<RecipeModel> recipes) {
        recipeList = recipes;
        recipeListAdapter.setRecipes(recipeList);
        showRecipes();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recipeRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showRecipes() {
        progressBar.setVisibility(View.GONE);
        recipeRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeList != null) {
            outState.putParcelableArrayList(RECIPES, new ArrayList<>(recipeList));
        }
    }

    private void setAppBarTitle(String title) {
        if (getActivity() instanceof RecipeListActivity) {
            ((RecipeListActivity) getActivity()).setAppBarTitle(title);
        }
    }

    @Override
    public void onClick(RecipeModel recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE, recipe);
        RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(bundle);
        if (getActivity() instanceof RecipeListActivity) {
            ((RecipeListActivity) getActivity()).addFragment(fragment, R.id.recipe_fragment_container,
                RecipeDetailsFragment.TAG);
        }
    }
}
