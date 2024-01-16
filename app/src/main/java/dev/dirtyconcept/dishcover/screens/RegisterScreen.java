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
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText usernameField = AnimationCommons.setupField(RegisterScreen.this, R.id.register_username, R.string.username);
        EditText emailField = AnimationCommons.setupField(RegisterScreen.this, R.id.register_email, R.string.email);
        EditText passwordField = AnimationCommons.setupField(RegisterScreen.this, R.id.register_password, R.string.password);
        EditText repasswordField = AnimationCommons.setupField(RegisterScreen.this, R.id.register_repassword, R.string.reenter_password);

        Button registerButton = findViewById(R.id.register);
        Button redirectLoginButton = findViewById(R.id.redirect_login);
        TextView textViewErrorMessage = findViewById(R.id.error_message);

        registerButton.setOnClickListener(v ->
                attemptRegistration(
                        usernameField.getText().toString(),
                        emailField.getText().toString(),
                        passwordField.getText().toString(),
                        repasswordField.getText().toString(),
                        textViewErrorMessage
                )
        );

        redirectLoginButton.setOnClickListener(v -> redirectTo(LoginScreen.class));
    }

    private void attemptRegistration(String username, String email, String password,
                                     String repassword, TextView errorMessageTextView) {
        errorMessageTextView.setText("");

        if (ValidationUtils.isEmpty(username, email, password)) {
            errorMessageTextView.setText(R.string.empty_fields);
            return;
        }

        if (!isValidFormat(username, email, password)) {
            errorMessageTextView.setText(R.string.wrong_format);
            return;
        }

        if (!Objects.equals(password, repassword)) {
            errorMessageTextView.setText(R.string.register_user_conflict);
            return;
        }

        setSignupButtonState(false);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult().getUser() != null) {
                        redirectTo(HomeScreen.class);
                    } else {
                        handleRegistrationFailure(task.getException(), errorMessageTextView);
                    }
                    setSignupButtonState(true);
                });
    }

    private boolean isValidFormat(String username, String email, String password) {
        return ValidationUtils.isValidUsername(username) &&
                ValidationUtils.isValidEmail(email) &&
                ValidationUtils.isValidPassword(password);
    }

    private void handleRegistrationFailure(Exception exception, TextView errorMessageTextView) {
        if (exception == null) {
            errorMessageTextView.setText(R.string.register_failed);
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorMessageTextView.setText(R.string.register_user_conflict);
        } else if (exception instanceof FirebaseTooManyRequestsException) {
            errorMessageTextView.setText(getString(R.string.too_many_attempts, getString(R.string.register)));
        } else {
            errorMessageTextView.setText(R.string.register_failed);
        }
    }

    private void setSignupButtonState(boolean enabled) {
        findViewById(R.id.register).setEnabled(enabled);
    }

    private void redirectTo(Class<?> destinationClass) {
        Intent intent = new Intent(RegisterScreen.this, destinationClass);
        startActivity(intent);
        finish();
    }
}
