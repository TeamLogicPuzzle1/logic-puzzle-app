package com.cookandroid.test_ui.mainPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.test_ui.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe_item> recipeList;

    public RecipeAdapter(List<Recipe_item> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe_item recipe = recipeList.get(position);
        holder.recipeNameText.setText(recipe.getName()); // 요리 이름 설정
        holder.recipeIngredientText.setText(recipe.getIngredients()); // 재료 설정
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameText;
        TextView recipeIngredientText;
        CardView cardView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameText = itemView.findViewById(R.id.RecipeNameText);
            recipeIngredientText = itemView.findViewById(R.id.RecipeIngredientNameText);
            cardView = itemView.findViewById(R.id.AddItemLayout);
        }
    }
}
