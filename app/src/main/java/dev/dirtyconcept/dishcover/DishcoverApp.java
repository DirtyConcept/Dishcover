package dev.dirtyconcept.dishcover;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class DishcoverApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
