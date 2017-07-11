package com.udacity.rokas.cookingguide.recipeStep;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.RecipeListActivity;
import com.udacity.rokas.cookingguide.RecipeListFragment;
import com.udacity.rokas.cookingguide.models.RecipeModel;
import com.udacity.rokas.cookingguide.models.StepModel;
import com.udacity.rokas.cookingguide.recipeDetails.RecipeDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rokas on 7/3/17.
 */

public class RecipeStepFragment extends Fragment {

    public static final String TAG = RecipeStepFragment.class.getCanonicalName();

    @BindView(R.id.player_view) SimpleExoPlayerView playerView;
    @BindView(R.id.recipe_step_details) TextView stepDetailsView;
    @BindView(R.id.recipe_step_previous) Button previousButton;
    @BindView(R.id.recipe_step_next) Button nextButton;

    private SimpleExoPlayer player;
    private StepModel step;
    private RecipeModel recipe;

    public static RecipeStepFragment newInstance(Bundle bundle) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(bundle);
        return recipeStepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.containsKey(RecipeDetailsFragment.STEP)) {
                step = bundle.getParcelable(RecipeDetailsFragment.STEP);
                recipe = bundle.getParcelable(RecipeListFragment.RECIPE);
            }
        } else if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(RecipeDetailsFragment.STEP);
            recipe = savedInstanceState.getParcelable(RecipeListFragment.RECIPE);

        } else {
            // do something
        }

        if (step != null) {
            setAppBarTitle(com.udacity.rokas.cookingguide.utilities.TextUtils.getStepTitle(step));
            stepDetailsView.setText(step.getDescription());
            initializePlayer();

            // set up the buttons
            setupButtons();
        }

        return view;
    }

    private void initializePlayer() {
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());

            playerView.setPlayer(player);
            player.setPlayWhenReady(true);
            Uri mediaUri = Uri.parse(step.getVideoURL());
            player.prepare(buildMediaSource(mediaUri), true, false);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private void setupButtons() {
        if (step.getId() >= recipe.getIngredientList().size()) {
            nextButton.setEnabled(false);
        } else if (step.getId() <= 0) {
            previousButton.setEnabled(false);
        }
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArgs(-1);
                RecipeStepFragment fragment = RecipeStepFragment.newInstance(bundle);
                replaceFragment(fragment);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArgs(1);
                RecipeStepFragment fragment = RecipeStepFragment.newInstance(bundle);
                replaceFragment(fragment);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (step != null) {
            outState.putParcelable(RecipeDetailsFragment.STEP, step);
            outState.putParcelable(RecipeListFragment.RECIPE, recipe);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
            new DefaultHttpDataSourceFactory("ua"),
            new DefaultExtractorsFactory(), null, null);
    }

    private void setAppBarTitle(String title) {
        if (getActivity() instanceof RecipeListActivity) {
            ((RecipeListActivity) getActivity()).setAppBarTitle(title);
            ((RecipeListActivity) getActivity()).setUpAppBarBackButton(true);
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() instanceof RecipeListActivity) {
            ((RecipeListActivity) getActivity()).replaceFragment(fragment, R.id.recipe_fragment_container, RecipeStepFragment.TAG, this);
//            ((RecipeListActivity) getActivity()).addFragment(fragment, R.id.recipe_fragment_container, RecipeStepFragment.TAG, false);
        }
    }

    private Bundle getArgs(int offset) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailsFragment.STEP, recipe.getStepList().get(step.getId() + offset));
        bundle.putParcelable(RecipeListFragment.RECIPE, recipe);
        return bundle;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
    //    for portrait mode
//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//            | View.SYSTEM_UI_FLAG_FULLSCREEN
//            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }
}