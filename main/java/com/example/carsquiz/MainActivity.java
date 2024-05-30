package com.example.carsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView register_text_ref, forgot_password;

    private FirebaseAuth m_auth;
    Button login_button;
    CheckBox remember_me;
    EditText email_address, password;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = m_auth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        forgot_password = findViewById(R.id.forgot_password);
        m_auth = FirebaseAuth.getInstance();
        remember_me = findViewById(R.id.remember_me);
        register_text_ref = findViewById(R.id.register_now);
        email_address = findViewById(R.id.email_address);
        password = findViewById(R.id.enter_password);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(view -> {
            checkCredentials();
        });
        register_text_ref.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void checkCredentials() {
        String checkEmail = email_address.getText().toString().trim();
        String checkPassword = password.getText().toString().trim();

        boolean isValid = true;

        if (checkEmail.isEmpty()) {
            showError(email_address, "Please enter your email.");
            isValid = false;
        }
        else if (!checkEmail.contains("@") && !checkEmail.contains(".")) {
            showError(email_address, "Please enter a valid email address.");
            isValid = false;
        }
        else if (checkPassword.isEmpty()) {
            showError(password, "Please enter your password.");
            isValid = false;
        }
        else if (checkPassword.length() < 8) {
            showError(password, "Your password length must be at least 8 characters.");
            isValid = false;
        }
        else if (checkPassword.contains(" ")) {
            showError(password, "Your password should not contain spaces.");
            isValid = false;
        }
        else if (checkPassword.length() > 64) {
            showError(password, "Your password can have at most 64 characters.");
            isValid = false;
        }

        if (isValid) {
            m_auth.signInWithEmailAndPassword(checkEmail, checkPassword)
                    .addOnCompleteListener(this, task -> {

                        if (task.isSuccessful()) {
                            FirebaseUser user = m_auth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                Toast.makeText(MainActivity.this, "You have successfully logged in.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Please verify your email address.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid email or password, please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        forgot_password.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void showError(@NonNull EditText input, String errorText) {
        input.setError(errorText);
        input.requestFocus();
    }
}