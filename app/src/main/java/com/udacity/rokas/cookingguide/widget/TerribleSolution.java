package com.udacity.rokas.cookingguide.widget;

/**
 * Created by rokas on 8/3/17.
 */

public class TerribleSolution {
    
    private static int currentRecipe = 0;

    private TerribleSolution() {
        // hiding constructor
    }

    public static int getCurrentRecipe() {
        return currentRecipe;
    }

    public static void setCurrentRecipe(int currentRecipe) {
        TerribleSolution.currentRecipe = currentRecipe;
    }
}
