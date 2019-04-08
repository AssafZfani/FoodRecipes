package zfani.assaf.food_recipes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipesData {

    @SerializedName("count")
    public int count;

    @SerializedName("recipes")
    public List<Recipe> recipeList;
}
