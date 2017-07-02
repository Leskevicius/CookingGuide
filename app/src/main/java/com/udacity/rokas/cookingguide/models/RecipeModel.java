package com.udacity.rokas.cookingguide.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by rokas on 7/1/17.
 *
 * This model represents a recipe in a list of recipes that are returned by Udacity API
 */

public class RecipeModel implements Parcelable {

    private int id;
    private String name;
    private int servings;
    private String image;
    private List<IngredientModel> ingredientList;
    private List<StepModel> stepList;

    public RecipeModel() {}

    protected RecipeModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        in.readList(ingredientList, IngredientModel.class.getClassLoader());
        in.readList(stepList, StepModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeList(ingredientList);
        dest.writeList(stepList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<IngredientModel> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientModel> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<StepModel> getStepList() {
        return stepList;
    }

    public void setStepList(List<StepModel> stepList) {
        this.stepList = stepList;
    }
}
