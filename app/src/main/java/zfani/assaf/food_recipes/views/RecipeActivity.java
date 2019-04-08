package zfani.assaf.food_recipes.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import zfani.assaf.food_recipes.R;

public class RecipeActivity extends AppCompatActivity {

    public static final String KEY_RECIPE_ID = "recipe_id";

    /*@BindView(R.id.wvRecipe)
    WebView wvRecipe;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        //String recipeId = getIntent().getStringExtra(KEY_RECIPE_ID);
    }
}
