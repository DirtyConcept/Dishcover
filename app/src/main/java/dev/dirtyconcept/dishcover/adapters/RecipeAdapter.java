package dev.dirtyconcept.dishcover.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.dirtyconcept.dishcover.R;
import dev.dirtyconcept.dishcover.data.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private OnItemClickListener onItemClickListener;

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public Recipe getItem(int position) {
        return recipes.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewDescription;
        private Button buttonDetails;

        RecipeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textName);
            textViewDescription = itemView.findViewById(R.id.textDescription);
            buttonDetails = itemView.findViewById(R.id.buttonOpen);

            buttonDetails.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, position);
                    }
                }
            });
        }

        void bind(Recipe recipe) {
            textViewName.setText(recipe.getName());
            textViewDescription.setText(recipe.getDescription());
        }
    }
}