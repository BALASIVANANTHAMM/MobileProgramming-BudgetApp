package com.example.budgetplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.budgetplan.databinding.ActivityUpdationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class updation extends AppCompatActivity {
    ActivityUpdationBinding binding;
    String newCheck;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        String id=getIntent().getStringExtra("id");
        String amount=getIntent().getStringExtra("amount");
        String note=getIntent().getStringExtra("note");
        String check=getIntent().getStringExtra("check");

        binding.userAmount.setText(amount);
        binding.userNote.setText(note);

        switch (check){
            case "Income":
                newCheck="Income";
                binding.checkIncome.setChecked(true);
                break;
            case "Expense":
                newCheck="Expense";
                binding.checkExpense.setChecked(true);
                break;
        }
        binding.checkIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCheck="Income";
                binding.checkIncome.setChecked(true);
                binding.checkExpense.setChecked(false);
            }
        });
        binding.checkExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCheck="Expense";
                binding.checkIncome.setChecked(false);
                binding.checkExpense.setChecked(true);
            }
        });
        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=binding.userAmount.getText().toString();
                String note=binding.userNote.getText().toString();

                firebaseFirestore.collection("Expense").document(firebaseAuth.getUid())
                        .collection("Note").document(id)
                        .update("amount",amount,"note",note,"check",check)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(updation.this, "UPDATED", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Expense").document(firebaseAuth.getUid())
                        .collection("Note").document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(updation.this, "DELETED", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}