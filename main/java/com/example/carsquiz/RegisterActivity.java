package com.example.carsquiz;

import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth f_auth;
    FirebaseFirestore f_store;
    TextView register_text_ref;
    Button register_button;
    EditText enter_email, enter_password, confirm_password;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        f_auth = FirebaseAuth.getInstance();
        f_store = FirebaseFirestore.getInstance();
        register_button = findViewById(R.id.register_button);
        enter_email = findViewById(R.id.enter_email);
        enter_password = findViewById(R.id.enter_password);
        confirm_password = findViewById(R.id.confirm_password);
        register_text_ref = findViewById(R.id.register_now);
        register_text_ref.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        register_button.setOnClickListener(view -> {
            checkingCredentials();
        });
    }

    private void checkingCredentials() {
        String checkEmail = enter_email.getText().toString();
        String checkPassword = enter_password.getText().toString();
        String checkConfirmedPassword = confirm_password.getText().toString();

        boolean isValid = true;

        if (checkEmail.isEmpty()) {
            showError(enter_email, "Please enter your email.");
            isValid = false;
        }
        else if (!checkEmail.contains("@") && !checkEmail.contains(".") || !checkEmail.contains("@") || !checkEmail.contains(".")) {
            showError(enter_email, "Please enter a valid email address.");
            isValid = false;
        }
        else if (checkPassword.isEmpty()) {
            showError(enter_password, "Please enter your password.");
            isValid = false;
        }
        else if (checkPassword.length() < 8) {
            showError(enter_password, "Your password length must be at least 8 characters.");
            isValid = false;
        }
        else if (checkPassword.contains(" ")) {
            showError(enter_password, "Your password should not contain spaces.");
            isValid = false;
        }
        else if (checkPassword.length() > 64) {
            showError(enter_password, "Your password can have at most 64 characters.");
            isValid = false;
        }
        else if (checkConfirmedPassword.isEmpty()) {
            showError(confirm_password, "Please confirm your password.");
            isValid = false;
        }
        else if (!checkConfirmedPassword.equals(checkPassword)) {
            showError(confirm_password, "Your password doesn't match the previous one.");
            isValid = false;
        }

        if (isValid) {
            f_auth.createUserWithEmailAndPassword(checkEmail, checkPassword)
                    .addOnCompleteListener(this, task -> {

                        if (task.isSuccessful()) {
                            FirebaseUser f_user = f_auth.getCurrentUser();
                            if (f_user != null) {
                                f_user.sendEmailVerification()
                                        .addOnSuccessListener(unused -> {
                                            Toast.makeText(RegisterActivity.this, "Email verification link sent to your email.", Toast.LENGTH_SHORT).show();
                                            completeRegistration(checkEmail);
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d(TAG, "Email not sent" + e.getMessage());
                                            Toast.makeText(RegisterActivity.this, "Failed to send email verification link.", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to get current user.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "This email is already in use. Please enter another one.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void completeRegistration(String emailAddress) {
        Toast.makeText(RegisterActivity.this, "You have successfully registered", Toast.LENGTH_SHORT).show();

        userID = Objects.requireNonNull(f_auth.getCurrentUser()).getUid();
        DocumentReference documentReference = f_store.collection("all my users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("Email address", emailAddress);

        documentReference.set(user)
                .addOnSuccessListener(unused -> Log.d(TAG, "User profile has been created for " + userID))
                .addOnFailureListener(e -> Log.d(TAG, e.toString()));

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void showError(EditText input, String errorText) {
        input.setError(errorText);
        input.requestFocus();
    }
}