package zfani.assaf.food_recipes.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zfani.assaf.food_recipes.R;
import zfani.assaf.food_recipes.adapters.RecipeAdapter;
import zfani.assaf.food_recipes.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etSearch)
    TextInputEditText etSearch;
    @BindView(R.id.btnSearch)
    View btnSearch;
    @BindView(R.id.rvRecipes)
    RecyclerView rvRecipes;
    @BindView(R.id.skvProgress)
    SpinKitView skvProgress;
    @BindView(R.id.tvNoResultsMessage)
    TextView tvNoResultsMessage;
    private MainViewModel mainViewModel;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initEditText();
        initRecyclerView();
        observeData();
    }

    @OnClick(R.id.btnSearch)
    public void search() {
        skvProgress.setVisibility(View.VISIBLE);
        mainViewModel.searchQuery(String.valueOf(etSearch.getText()));
    }

    private void initEditText() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    btnSearch.setVisibility(View.GONE);
                    search();
                } else {
                    btnSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRecyclerView() {
        rvRecipes.setAdapter(recipeAdapter = new RecipeAdapter());
        recipeAdapter.setOnRecipeSelectedListener(recipeId -> startActivity(new Intent(MainActivity.this, RecipeActivity.class).putExtra(RecipeActivity.KEY_RECIPE_ID, recipeId)));
        rvRecipes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) Objects.requireNonNull(rvRecipes.getLayoutManager())).findLastVisibleItemPosition();
                if (lastVisibleItem == mainViewModel.getCurrentItemsPerPage() - 1) {
                    skvProgress.setVisibility(View.VISIBLE);
                    mainViewModel.moveToNextPage();
                }
            }
        });
    }

    private void observeData() {
        mainViewModel.getRecipeList().observe(this, recipeList -> {
            skvProgress.setVisibility(View.GONE);
            tvNoResultsMessage.setVisibility(recipeList == null || recipeList.isEmpty() ? View.VISIBLE : View.GONE);
            recipeAdapter.submitList(recipeList);
        });
    }
}
