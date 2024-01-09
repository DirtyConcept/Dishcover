package dev.dirtyconcept.dishcover.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

import dev.dirtyconcept.dishcover.R;
import dev.dirtyconcept.dishcover.utils.AnimationCommons;
import dev.dirtyconcept.dishcover.utils.ValidationUtils;

public class RegisterScreen extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText usernameField = AnimationCommons.setupField(this, R.id.register_username, R.string.username);
        EditText emailField = AnimationCommons.setupField(this, R.id.register_email, R.string.email);
        EditText passwordField = AnimationCommons.setupField(this, R.id.register_password, R.string.password);
        EditText repasswordField = AnimationCommons.setupField(this, R.id.register_repassword, R.string.reenter_password);

        Button registerButton = findViewById(R.id.register);
        Button redirectLoginButton = findViewById(R.id.redirect_login);
        TextView textViewErrorMessage = findViewById(R.id.error_message);

        registerButton.setOnClickListener(v -> {
            textViewErrorMessage.setText("");

            String username = usernameField.getText().toString();
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (ValidationUtils.isEmpty(username, email, password)) {
                textViewErrorMessage.setText(R.string.empty_fields);
                return;
            }

            if (!isValidFormat(username, email, password)) {
                textViewErrorMessage.setText(R.string.wrong_format);
                return;
            }

            if (!Objects.equals(passwordField.getText().toString(), repasswordField.getText().toString())) {
                textViewErrorMessage.setText(R.string.register_user_conflict);
                return;
            }

            registerButton.setEnabled(false);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful() && task.getResult().getUser() != null) {
                            Intent main = new Intent(this, MainScreen.class);
                            startActivity(main);
                            finish();
                        } else {
                            handleLoginFailure(task.getException(), textViewErrorMessage);
                        }
                        registerButton.setEnabled(true);
                    });
        });

        redirectLoginButton.setOnClickListener(v -> {
            Intent register = new Intent(this, LoginScreen.class);
            startActivity(register);
            finish();
        });
    }

    private boolean isValidFormat(String username, String email, String password) {
        return ValidationUtils.isValidUsername(username) &&
                ValidationUtils.isValidEmail(email) &&
                ValidationUtils.isValidPassword(password);
    }

    private void handleLoginFailure(Exception exception, TextView errorMessageTextView) {
        if (exception == null) {
            errorMessageTextView.setText(R.string.login_failed);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorMessageTextView.setText(R.string.register_user_conflict);
        } else if (exception instanceof FirebaseTooManyRequestsException) {
            errorMessageTextView.setText(getString(R.string.too_many_attempts, getString(R.string.register)));
        } else {
            errorMessageTextView.setText(R.string.register_failed);
        }
    }
}
