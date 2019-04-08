package zfani.assaf.food_recipes.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import zfani.assaf.food_recipes.models.RecipesData;

public interface APIInterface {

    @POST("/api/search?")
    Call<RecipesData> searchRecipe(@Query("key") String apiKey, @Query("page") String page, @Query("q") String q);
}
