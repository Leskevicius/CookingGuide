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

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.rokas.cookingguide.R;
import com.udacity.rokas.cookingguide.RecipeActivity;
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

    private static final String SEEK_POSITION = "seekPosition";

    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;

    // These are nullable so I allow for ButterKnife to work when I don't actually need these elements (landscape)
    @Nullable
    @BindView(R.id.recipe_step_details)
    TextView stepDetailsView;

    @Nullable
    @BindView(R.id.recipe_step_previous)
    Button previousButton;

    @Nullable
    @BindView(R.id.recipe_step_next)
    Button nextButton;

    @Nullable
    @BindView(R.id.recipe_step_button_container)
    CardView buttonContainer;

    @Nullable
    @BindView(R.id.recipe_step_landscape_no_video)
    TextView noVideoText;

    @Nullable
    @BindView(R.id.recipe_steps_image)
    ImageView recipeImage;

    private SimpleExoPlayer player;

    private long seekPosition = -1;

    private StepModel step;

    private RecipeModel recipe;

    private boolean isTablet;

    public static RecipeStepFragment newInstance(Bundle bundle) {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(bundle);
        return recipeStepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        isTablet = getResources().getBoolean(R.bool.isTablet);

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

        if (savedInstanceState != null && savedInstanceState.containsKey(SEEK_POSITION)) {
            seekPosition = savedInstanceState.getLong(SEEK_POSITION);
        }

        // in landscape mode.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            view = inflater.inflate(R.layout.recipe_steps_fragment_landscape, container, false);
            ButterKnife.bind(this, view);

            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            if (TextUtils.isEmpty(step.getVideoURL())) {
                noVideoText.setText(getString(R.string.recipe_step_no_video));
                noVideoText.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.GONE);
            } else {
                initializePlayer();
            }

            showStep();

        } else {
            view = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
            ButterKnife.bind(this, view);

            if (step != null) {
                stepDetailsView.setText(step.getDescription());
                // set up the buttons
                setupButtons();
            }

            if (recipe != null && !TextUtils.isEmpty(step.getThumbnailURL())) {
                Glide.with(getContext()).load(step.getThumbnailURL()).into(recipeImage);
            }

            initializePlayer();

            if (!isTablet) {
                setAppBarTitle(com.udacity.rokas.cookingguide.utilities.TextUtils.getStepTitle(step));
                showStep();
            }
        }

        return view;
    }

    private void initializePlayer() {
        if (player == null) {
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

                playerView.setPlayer(player);
                player.setPlayWhenReady(true);
                Uri mediaUri = Uri.parse(step.getVideoURL());
                if (seekPosition != -1) {
                    player.seekTo(seekPosition);
                }
                player.prepare(buildMediaSource(mediaUri), true, false);
            } else {
                playerView.setVisibility(View.GONE);
            }
        }
    }

    private void setupButtons() {
        if (step.getId() >= recipe.getStepList().size() - 1) {
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

        outState.putLong(SEEK_POSITION, seekPosition);
    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
            new DefaultHttpDataSourceFactory("ua"),
            new DefaultExtractorsFactory(), null, null);
    }

    private void setAppBarTitle(String title) {
        if (getActivity() instanceof RecipeActivity) {
            ((RecipeActivity) getActivity()).setAppBarTitle(title);
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() instanceof RecipeActivity) {
            ((RecipeActivity) getActivity()).replaceFragment(fragment, R.id.recipe_step_fragment_container, RecipeStepFragment.TAG, this);
        }
    }

    private Bundle getArgs(int offset) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailsFragment.STEP, recipe.getStepList().get(step.getId() + offset));
        bundle.putParcelable(RecipeListFragment.RECIPE, recipe);
        return bundle;
    }

    private void showStep() {
        if (getActivity() instanceof RecipeActivity) {
            ((RecipeActivity) getActivity()).showStep();
        }
    }

    @Override
    public void onStop() {
        Log.v("Rokas: ", "stop");
        super.onStop();
        if (player != null) {
            seekPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        Log.v("Rokas: ", "pause");
        super.onPause();
        if (player != null) {
            seekPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onResume() {
        Log.v("Rokas: ", "resume");
        super.onResume();
        initializePlayer();
    }
}
