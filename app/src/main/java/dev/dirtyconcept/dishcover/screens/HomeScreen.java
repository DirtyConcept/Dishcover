package dev.dirtyconcept.dishcover.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import dev.dirtyconcept.dishcover.R;
import dev.dirtyconcept.dishcover.data.Recipe;

public class HomeScreen extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesCollection = db.collection("recipes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve 5 recipes
        getRecipes();
    }

    private void getRecipes() {
        recipesCollection.limit(5).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Recipe recipe = Recipe.fromFirestoreDocument(document);
                            handleRecipe(recipe);
                        }
                    } else {
                        handleFirestoreError(task.getException());
                    }
                });
    }

    private void handleRecipe(Recipe recipe) {
    }

    private void handleFirestoreError(Exception exception) {
    }
}
