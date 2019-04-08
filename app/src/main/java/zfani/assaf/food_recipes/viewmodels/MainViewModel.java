package zfani.assaf.food_recipes.viewmodels;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zfani.assaf.food_recipes.R;
import zfani.assaf.food_recipes.models.Recipe;
import zfani.assaf.food_recipes.models.RecipesData;
import zfani.assaf.food_recipes.retrofit.APIClient;
import zfani.assaf.food_recipes.retrofit.APIInterface;

public class MainViewModel extends AndroidViewModel {

    private APIInterface apiInterface;
    private MutableLiveData<List<Recipe>> recipeList;
    private String query;
    private int page = 1, itemsPerPage, currentItemsPerPage;

    public MainViewModel(@NonNull Application application) {
        super(application);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        recipeList = new MutableLiveData<>();
        loadRecipes();
    }

    private void loadRecipes() {
        apiInterface.searchRecipe(getApplication().getString(R.string.food2fork_api_key), String.valueOf(page), query).enqueue(new Callback<RecipesData>() {

            @Override
            public void onResponse(@NonNull Call<RecipesData> call, @NonNull Response<RecipesData> response) {
                RecipesData recipesData = response.body();
                if (recipesData == null) {
                    return;
                }
                itemsPerPage = recipesData.count;
                if (itemsPerPage == 0) {
                    currentItemsPerPage = 0;
                }
                currentItemsPerPage += itemsPerPage;
                if (page == 1) {
                    recipeList.postValue(recipesData.recipeList);
                } else {
                    List<Recipe> currentRecipeList = recipeList.getValue();
                    if (currentRecipeList != null) {
                        currentRecipeList.addAll(recipesData.recipeList);
                        recipeList.setValue(currentRecipeList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipesData> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }

    public void searchQuery(String query) {
        this.query = query;
        currentItemsPerPage = 0;
        page = 1;
        recipeList.setValue(new ArrayList<>());
        loadRecipes();
    }

    public void moveToNextPage() {
        this.page++;
        loadRecipes();
    }

    public int getCurrentItemsPerPage() {
        return currentItemsPerPage;
    }
}
