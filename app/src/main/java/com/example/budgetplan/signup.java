package com.example.budgetplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.budgetplan.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent L = new Intent(signup.this, MainActivity.class);
                try {
                    startActivity(L);
                } catch (Exception e) {

                }
            }
        });

        binding.btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailSign.getText().toString();
                String password = binding.passSign.getText().toString();
                if (email.trim().length() <= 0 || password.trim().length() <= 0) {
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(signup.this, "Successfully Create the User", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}