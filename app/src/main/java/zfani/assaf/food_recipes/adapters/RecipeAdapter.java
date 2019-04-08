package zfani.assaf.food_recipes.adapters;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import zfani.assaf.food_recipes.R;
import zfani.assaf.food_recipes.models.Recipe;

public class RecipeAdapter extends ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {

    private OnRecipeSelectedListener onRecipeSelectedListener;

    public RecipeAdapter() {
        super(new DiffUtil.ItemCallback<Recipe>() {
            @Override
            public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
                return oldItem.recipeId.equalsIgnoreCase(newItem.recipeId);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
                return oldItem.title.equalsIgnoreCase(newItem.title) && oldItem.publisher.equalsIgnoreCase(newItem.publisher) && oldItem.imageUrl.equalsIgnoreCase(newItem.imageUrl);
            }
        });
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(View.inflate(parent.getContext(), R.layout.row_recipe, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = getItem(position);
        Glide.with(holder.itemView.getContext()).load(recipe.imageUrl).apply(new RequestOptions().placeholder(R.drawable.recipe_placeholder)).into(holder.ivImage);
        holder.tvRecipeTitle.setText(Html.fromHtml(recipe.title));
        holder.tvRecipePublisher.setText(recipe.publisher);
        holder.itemView.setOnClickListener(v -> {
            if (onRecipeSelectedListener != null) {
                onRecipeSelectedListener.onRecipeSelected(recipe.recipeId);
            }
        });
    }

    public void setOnRecipeSelectedListener(OnRecipeSelectedListener onRecipeSelectedListener) {
        this.onRecipeSelectedListener = onRecipeSelectedListener;
    }

    public interface OnRecipeSelectedListener {
        void onRecipeSelected(String recipeId);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvRecipeTitle)
        TextView tvRecipeTitle;
        @BindView(R.id.tvRecipePublisher)
        TextView tvRecipePublisher;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
