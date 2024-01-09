package dev.dirtyconcept.dishcover.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            redirectLogin();
            return;
        }

        user.reload().addOnCompleteListener(task -> {
            if (!task.isSuccessful() && auth.getCurrentUser() == null) redirectLogin();
            else redirectMain();
        });
    }

    public void redirectLogin() {
        Intent login = new Intent(this, LoginScreen.class);
        startActivity(login);
        finish();
    }

    public void redirectMain() {
        Intent main = new Intent(this, MainScreen.class);
        startActivity(main);
        finish();
    }
}
