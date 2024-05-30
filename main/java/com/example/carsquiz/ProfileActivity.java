package com.example.carsquiz;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    Button speed, more_hp, expensive_one,older_one, log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        fAuth = FirebaseAuth.getInstance();
        log_out = findViewById(R.id.log_out_button);
        speed = findViewById(R.id.speed);
        more_hp = findViewById(R.id.more_hp);
        expensive_one = findViewById(R.id.expensive_one);
        older_one = findViewById(R.id.older_one);
        speed.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, SpeedActivity.class);
            startActivity(intent);
        });

        more_hp.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, hpActivity.class);
            startActivity(intent);
        });

        expensive_one.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ExpenseActivity.class);
            startActivity(intent);
        });

        older_one.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, oldActivity.class);
            startActivity(intent);
        });

        log_out.setOnClickListener(v -> {
            fAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}