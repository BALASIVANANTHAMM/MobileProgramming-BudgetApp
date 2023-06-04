package com.example.budgetplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.budgetplan.databinding.ActivityAddtransactionBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class addtransaction extends AppCompatActivity {
ActivityAddtransactionBinding binding;
FirebaseFirestore firestore;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
String check="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddtransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        binding.checkExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check="Expense";
                binding.checkExpense.setChecked(true);
                binding.checkIncome.setChecked(false);
            }
        });

        binding.checkIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check="Income";
                binding.checkExpense.setChecked(false);
                binding.checkIncome.setChecked(true);
            }
        });

        binding.AddBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=binding.userAmount.getText().toString().trim();
                String note=binding.userNote.getText().toString().trim();
                if (amount.length()<=0){
                    return;
                }
                if (check.length()<=0){
                    Toast.makeText(addtransaction.this, "Select the Transaction Type", Toast.LENGTH_SHORT).show();
                }
                SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy  HH:mm:SS", Locale.getDefault());
                String CurrentDate = SDF.format(new Date());

                String id = UUID.randomUUID().toString();
                Map<String,Object> transaction=new HashMap<>();
                transaction.put("id",id);
                transaction.put("amount",amount);
                transaction.put("note",note);
                transaction.put("check",check);
                transaction.put("time",CurrentDate);
                firestore.collection("Expense").document(firebaseAuth.getUid()).collection("Note").document(id).set(transaction)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(addtransaction.this, "Added", Toast.LENGTH_SHORT).show();
                                binding.userNote.setText("");
                                binding.userAmount.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addtransaction.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}