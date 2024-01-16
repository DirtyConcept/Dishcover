package dev.dirtyconcept.dishcover.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            registerTo(LoginScreen.class);
            return;
        }

        user.reload().addOnCompleteListener(task -> {
            if (!task.isSuccessful() && auth.getCurrentUser() == null) registerTo(LoginScreen.class);
            else registerTo(HomeScreen.class);
        });
    }

    public void registerTo(Class<?> destinationClass) {
        Intent login = new Intent(this, destinationClass);
        startActivity(login);
        finish();
    }
}
