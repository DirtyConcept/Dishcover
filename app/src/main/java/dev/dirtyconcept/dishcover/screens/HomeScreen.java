package dev.dirtyconcept.dishcover.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.dirtyconcept.dishcover.R;
import dev.dirtyconcept.dishcover.adapters.RecipeAdapter;
import dev.dirtyconcept.dishcover.data.Recipe;

public class HomeScreen extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesCollection = db.collection("recipes");

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recipeAdapter = new RecipeAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);

        // Fetch 6 recipes from Firestore
        fetchRecipes();

        // Set click listener for recipe items
        recipeAdapter.setOnItemClickListener((view, position) -> {
            Recipe selectedRecipe = recipeAdapter.getItem(position);
            // Implement your logic to open a detailed view for the selected recipe
            // You can pass the selected recipe to the next activity or fragment
            // Example: openDetailedView(selectedRecipe);
        });
    }

    private void fetchRecipes() {
        recipesCollection.limit(6)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Recipe> recipes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Recipe recipe = document.toObject(Recipe.class);
                        recipes.add(recipe);
                    }
                    handleRecipes(recipes);
                })
                .addOnFailureListener(this::handleFirestoreError);
    }

    private void handleRecipes(List<Recipe> recipes) {
        // Update the UI with the fetched recipes
        recipeAdapter.setRecipes(recipes);
        recipeAdapter.notifyDataSetChanged();
    }

    private void handleFirestoreError(Exception exception) {
    }
}
