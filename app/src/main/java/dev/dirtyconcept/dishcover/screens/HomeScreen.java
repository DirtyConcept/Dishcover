package dev.dirtyconcept.dishcover.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convert the Firestore document to a Recipe
                                Recipe recipe = Recipe.fromFirestoreDocument(document);

                                // Handle the retrieved recipe (e.g., add it to a list or display it)
                                handleRecipe(recipe);
                            }
                        } else {
                            // Handle errors
                            handleFirestoreError(task.getException());
                        }
                    }
                });
    }

    private void handleRecipe(Recipe recipe) {
        // Handle the retrieved recipe (e.g., add it to a list or display it)
        // TODO: Implement your logic here
    }

    private void handleFirestoreError(Exception exception) {
        // Handle Firestore errors
        // TODO: Implement your error handling logic here
    }
}
