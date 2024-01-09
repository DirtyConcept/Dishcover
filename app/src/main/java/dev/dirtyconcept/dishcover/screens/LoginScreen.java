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

import java.util.regex.Pattern;

import dev.dirtyconcept.dishcover.R;
import dev.dirtyconcept.dishcover.utils.AnimationCommons;
import dev.dirtyconcept.dishcover.utils.ValidationUtils;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailField = AnimationCommons.setupField(this, R.id.login_email, R.string.email);
        EditText passwordField = AnimationCommons.setupField(this, R.id.login_password, R.string.password);

        Button loginButton = findViewById(R.id.login);
        TextView textViewErrorMessage = findViewById(R.id.error_message);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (ValidationUtils.isEmpty(email, password)) {
                textViewErrorMessage.setText(R.string.empty_fields);
                return;
            }

            if (!isValidFormat(email, password)) {
                textViewErrorMessage.setText(R.string.wrong_format);
                return;
            }

            textViewErrorMessage.setText("");
            loginButton.setEnabled(false);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful() && task.getResult().getUser() != null) {
                            Intent main = new Intent(this, MainScreen.class);
                            startActivity(main);
                            finish();
                        } else {
                            handleLoginFailure(task.getException(), textViewErrorMessage);
                        }
                        loginButton.setEnabled(true);
                    });
        });

        Button redirectRegisterButton = findViewById(R.id.redirect_register);
        redirectRegisterButton.setOnClickListener(v -> {
            Intent register = new Intent(this, RegisterScreen.class);
            startActivity(register);
            finish();
        });
    }

    private boolean isValidFormat(String email, String password) {
        return ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password);
    }

    private void handleLoginFailure(Exception exception, TextView errorMessageTextView) {
        if (exception == null) {
            errorMessageTextView.setText(R.string.login_failed);
        } else if (exception instanceof FirebaseAuthInvalidUserException) {
            if (((FirebaseAuthInvalidUserException) exception).getErrorCode().equals("ERROR_USER_DISABLED")) {
                errorMessageTextView.setText(R.string.login_account_disabled);
            } else {
                errorMessageTextView.setText(R.string.login_failed);
            }
        } else if (exception instanceof FirebaseTooManyRequestsException) {
            errorMessageTextView.setText(R.string.too_many_attempts);
        }
    }
}
