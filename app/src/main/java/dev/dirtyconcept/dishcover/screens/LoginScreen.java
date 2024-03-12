package dev.dirtyconcept.dishcover.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import dev.dirtyconcept.dishcover.R;
import dev.dirtyconcept.dishcover.utils.AnimationCommons;
import dev.dirtyconcept.dishcover.utils.ValidationUtils;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailField = AnimationCommons.setupAuthField(LoginScreen.this, R.id.login_email, R.string.email);
        EditText passwordField = AnimationCommons.setupAuthField(LoginScreen.this, R.id.login_password, R.string.password);

        Button loginButton = findViewById(R.id.login);
        Button redirectRegisterButton = findViewById(R.id.redirect_register);
        TextView textViewErrorMessage = findViewById(R.id.error_message);

        loginButton.setOnClickListener(v ->
                attemptLogin(emailField.getText().toString(), passwordField.getText().toString(), textViewErrorMessage)
        );

        redirectRegisterButton.setOnClickListener(v -> redirectTo(RegisterScreen.class));
    }

    private void attemptLogin(String email, String password, TextView errorMessageTextView) {
        errorMessageTextView.setText("");

        if (ValidationUtils.isEmpty(email, password)) {
            errorMessageTextView.setText(R.string.empty_fields);
            return;
        }

        if (!isValidFormat(email, password)) {
            errorMessageTextView.setText(R.string.wrong_format);
            return;
        }

        setLoginButtonState(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult().getUser() != null) {
                        redirectTo(HomeScreen.class);
                    } else {
                        handleLoginFailure(task.getException(), errorMessageTextView);
                    }
                    setLoginButtonState(true);
                });
    }

    private boolean isValidFormat(String email, String password) {
        return ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password);
    }

    private void handleLoginFailure(Exception exception, TextView errorMessageTextView) {
        if (exception == null) {
            errorMessageTextView.setText(R.string.login_failed);
        } else if (exception instanceof FirebaseAuthInvalidUserException) {
            handleInvalidUser((FirebaseAuthInvalidUserException) exception, errorMessageTextView);
        } else if (exception instanceof FirebaseTooManyRequestsException) {
            errorMessageTextView.setText(getString(R.string.too_many_attempts, getString(R.string.login)));
        } else {
            errorMessageTextView.setText(R.string.login_failed);
        }
    }

    private void handleInvalidUser(FirebaseAuthInvalidUserException exception, TextView errorMessageTextView) {
        errorMessageTextView.setText(
                exception.getErrorCode().equals("ERROR_USER_DISABLED")
                ? R.string.login_account_disabled
                : R.string.login_failed
        );
    }

    private void setLoginButtonState(boolean enabled) {
        findViewById(R.id.login).setEnabled(enabled);
    }

    private void redirectTo(Class<?> destinationClass) {
        Intent intent = new Intent(LoginScreen.this, destinationClass);
        startActivity(intent);
        finish();
    }
}
