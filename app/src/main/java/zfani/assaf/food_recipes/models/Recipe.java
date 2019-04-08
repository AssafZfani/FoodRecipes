package zfani.assaf.food_recipes.models;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("recipe_id")
    public String recipeId;

    @SerializedName("title")
    public String title;

    @SerializedName("publisher")
    public String publisher;

    @SerializedName("image_url")
    public String imageUrl;
}
