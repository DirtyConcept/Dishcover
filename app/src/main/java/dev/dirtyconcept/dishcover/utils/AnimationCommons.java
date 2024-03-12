package dev.dirtyconcept.dishcover.utils;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationCommons {

    public static EditText setupAuthField(AppCompatActivity activity, int id, int hintId) {
        EditText field = activity.findViewById(id);
        field.setOnFocusChangeListener((v, state) -> {
            field.setHint(state ? "" : activity.getString(hintId));
        });
        return field;
    }
}
